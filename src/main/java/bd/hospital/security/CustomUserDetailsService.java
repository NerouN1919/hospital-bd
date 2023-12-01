package bd.hospital.security;

import bd.hospital.dto.UserDto;
import bd.hospital.repositories.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private HospitalRepository hospitalRepository;
    @Autowired
    public void setHospitalRepository(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = hospitalRepository.getUser(username);
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new UsernameNotFoundException("Введён неверный логин");
        }
        return User.withUsername(userDto.getUserName()).password(userDto.getPassword()).roles(userDto.getRoleName())
                .build();
    }
}
