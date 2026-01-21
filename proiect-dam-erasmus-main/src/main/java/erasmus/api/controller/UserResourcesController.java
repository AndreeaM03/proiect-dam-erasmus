package erasmus.api.controller;

import erasmus.api.dto.UserDTO;
import erasmus.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserResourcesController {

    private final UserService userService;

    public UserResourcesController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable String id,
                              @RequestBody UserDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.delete(id);
        return "User deleted";
    }
}
