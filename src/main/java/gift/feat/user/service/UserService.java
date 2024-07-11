package gift.feat.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gift.core.jwt.JwtProvider;
import gift.feat.user.User;
import gift.feat.user.repository.UserJpaRepository;

@Service
public class UserService {
	private final UserJpaRepository userJpaRepository;
	private final JwtProvider jwtProvider;

	public String checkEmailAndPassword(String email, String password) {
		User user = userJpaRepository.findByEmail(email);
		if (user == null) {
			return null; // TODO: 예외 처리
		}
		if (!user.getPassword().equals(password)) {
			return null; // TODO: 예외 처리
		}
		return jwtProvider.generateToken(user.getId().toString(), user.getRole());
	}

	public void registerUser(User user) {
		userJpaRepository.save(user);
	}

	@Autowired
	public UserService(UserJpaRepository userJpaRepository, JwtProvider jwtProvider) {
		this.userJpaRepository = userJpaRepository;
		this.jwtProvider = jwtProvider;
	}
}