package erasmus.service;

import erasmus.api.dto.UserDTO;
import erasmus.api.mapper.UserMapper;
import erasmus.domain.model.User;
import erasmus.domain.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }

    public UserDTO create(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    public UserDTO update(String id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());

        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
