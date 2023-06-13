package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.conversationmessagedto.ConversationDTO;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingsMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.ConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.respository.ProfileRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.StudentRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.TeacherRepository;
import codersbay.vienna.nachhilfe.wien.backend.respository.UserRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CoachingsMapper coachingsMapper;
    private final ConversationMapper conversationMapper;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public AuthResponse createAuthResponse(User user) {
        AuthResponse auth = new AuthResponse();
        auth.setUserId(user.getId());
        auth.setUserType(user.getUserType());
        auth.setEmail(user.getProfile().getEmail());
        auth.setFirstName(user.getFirstName());
        auth.setLastName(user.getLastName());
        auth.setBirthdate(user.getBirthdate());
        auth.setDescription(user.getDescription());
        auth.setImage(user.getProfile().getImageBase64());
        auth.setActive(user.getProfile().isActive());
        auth.setAverageRatingScore(user.getProfile().getAverageRatingScore());
        auth.setCoachings(coachingsMapper.toDTO(user).getCoachings());

        Set<ConversationDTO> conversationDtos = user.getConversations().stream()
                .map(conversationMapper::toDTO)
                .collect(Collectors.toSet());
        auth.setConversations(conversationDtos);

        return auth;
    }

    public AuthResponse createTeacherWithProfile(Teacher teacher) {
        Profile profile = teacher.getProfile();
        profile.setPassword(passwordEncoder.encode(teacher.getPassword()));
        profileRepository.save(profile);
        teacher.setProfile(profile);
        teacher.setRole(Role.ROLE_TEACHER);
        teacherRepository.save(teacher);

        AuthResponse auth = createAuthResponse(teacher);
        auth.setDistricts(teacher.getDisctricts());

        String jwtToken = jwtService.generateToken(teacher);
        auth.setToken(jwtToken);

        return auth;
    }

    public AuthResponse createStudentWithProfile(Student student) {
        Profile profile = student.getProfile();
        profile.setPassword(passwordEncoder.encode(student.getPassword()));
        profileRepository.save(profile);
        student.setProfile(profile);
        student.setRole(Role.ROLE_STUDENT);
        studentRepository.save(student);

        AuthResponse auth = createAuthResponse(student);

        String jwtToken = jwtService.generateToken(student);
        auth.setToken(jwtToken);

        return auth;
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = profileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"))
                .getUser();

        AuthResponse auth = createAuthResponse(user);

        String jwtToken = jwtService.generateToken(user);
        auth.setToken(jwtToken);

        return auth;
    }
}
