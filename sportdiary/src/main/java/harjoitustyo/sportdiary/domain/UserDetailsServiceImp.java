package harjoitustyo.sportdiary.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService  {
	private final UserRepository repository;

	public UserDetailsServiceImp(UserRepository repository) {
		this.repository = repository;
	}

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = repository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        User currUser = optionalUser.get();

        return new org.springframework.security.core.userdetails.User(
                currUser.getUsername(),
                currUser.getPasswordHash(),
                AuthorityUtils.createAuthorityList(currUser.getRole())
        );
    }
}