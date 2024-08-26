package controller;

import model.RegisterRequest;
import repository.RegisterRepository;
import model.User;
import service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") // Aplicando CORS para todos os métodos no nível da classe
public class RegisterController {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Tornar o encoder um bean gerenciado pelo Spring

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();

        // Validações básicas de entrada
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Nome, email e senha são obrigatórios.");
        }

        String encryptedPassword = passwordEncoder.encode(password);

        User userObject = new User(name, email, encryptedPassword);

        RegisterRepository.save(userObject);

        // Enviar email de confirmação
        String subject = "Confirmação de Registro";
        String text = String.format("Olá %s, seu registro foi efetuado com sucesso!", name);
        emailService.sendEmail(email, subject, text);

        return ResponseEntity.ok("Registro efetuado para o usuário: " + name);
    }
}
