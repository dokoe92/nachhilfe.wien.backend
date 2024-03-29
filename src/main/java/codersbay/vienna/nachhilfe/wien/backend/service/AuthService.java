package codersbay.vienna.nachhilfe.wien.backend.service;

import codersbay.vienna.nachhilfe.wien.backend.config.security.JwtService;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthRequest;
import codersbay.vienna.nachhilfe.wien.backend.dto.auth.AuthResponse;
import codersbay.vienna.nachhilfe.wien.backend.mapper.coachingmapper.CoachingMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.conversationmessagemapper.ConversationMapper;
import codersbay.vienna.nachhilfe.wien.backend.mapper.feedbackmapper.FeedbackMapper;
import codersbay.vienna.nachhilfe.wien.backend.model.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.*;
import codersbay.vienna.nachhilfe.wien.backend.respository.conversationmessagerepository.ConversationRepository;
import codersbay.vienna.nachhilfe.wien.backend.rest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static codersbay.vienna.nachhilfe.wien.backend.model.Role.ROLE_TEACHER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CoachingMapper coachingMapper;
    private final ConversationMapper conversationMapper;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final FeedbackMapper feedbackMapper;
    private final AdminRepository adminRepository;
    private final ConversationRepository conversationRepository;


    public AuthResponse createAuthResponse(User user) {
        AuthResponse auth = new AuthResponse();
        auth.setUserId(user.getId());
        auth.setUserType(user.getUserType());
        auth.setEmail(user.getProfile().getEmail());
        auth.setFirstName(user.getFirstName());
        auth.setLastName(user.getLastName());
        auth.setBirthdate(user.getBirthdate());
        auth.setDescription(user.getProfile().getDescription());
        auth.setImage(user.getProfile().getImageBase64());
        auth.setActive(user.getProfile().isActive());
        auth.setAverageRatingScore(user.getProfile().getAverageRatingScore());
        if (user.getCoachings() != null) {
            for (Coaching coaching : user.getCoachings()) {
                auth.getCoachings().add(coachingMapper.toDTO(coaching));
            }
        }

        if(user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            if (teacher.getFeedbacks() != null) {
                auth.setFeedbacks(teacher.getFeedbacks().stream().map(feedbackMapper::toDTO).collect(Collectors.toSet()));
                auth.setDistricts(teacher.getDistricts());
            }
        }

        if (user.getConversations() != null) {
            List<Conversation> conversations = conversationRepository.findUserConversationsOrderByLatestMessage(user.getId());
            auth.setConversations(conversations.stream().map(conversationMapper::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)));
        }

        auth.setAvailableSubjects(EnumSet.allOf(Subject.class));

        return auth;
    }

    public AuthResponse createTeacherWithProfile(Teacher teacher) {
        Profile profile = teacher.getProfile();
        profile.setPassword(passwordEncoder.encode(teacher.getPassword()));
        profileRepository.save(profile);
        teacher.setProfile(profile);
        teacher.setRole(ROLE_TEACHER);
        teacherRepository.save(teacher);

        AuthResponse auth = createAuthResponse(teacher);
        auth.setDistricts(teacher.getDistricts());

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

    public AuthResponse createAdminWithProfile(Admin admin) {
        Profile profile = admin.getProfile();
        profile.setPassword(passwordEncoder.encode(admin.getPassword()));
        profileRepository.save(profile);
        admin.setProfile(profile);
        admin.setRole(Role.ROLE_ADMIN);
        adminRepository.save(admin);

        AuthResponse auth = createAuthResponse(admin);

        String jwtToken = jwtService.generateToken(admin);
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

        if (user.getProfile().getDeleted()) {
            throw new AuthenticationException("User not found or deleted!") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }

        AuthResponse auth = createAuthResponse(user);

        String jwtToken = jwtService.generateToken(user);
        auth.setToken(jwtToken);

        return auth;
    }
}
