package kz.kuleshov.springStart.servises;

import kz.kuleshov.springStart.UserRepository;
import kz.kuleshov.springStart.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserServise implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findAllByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("username not found");
        }
        return user;
    }
}
