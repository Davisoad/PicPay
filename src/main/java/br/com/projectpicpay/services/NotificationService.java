package br.com.projectpicpay.services;

import br.com.projectpicpay.dtos.NotificationDTO;
import br.com.projectpicpay.model.entities.user.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

//    private final RestTemplate restTemplate;
//
//    public NotificationService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public void sendNotification(User user, String message) {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

//       ResponseEntity<String> notificationResponse = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);
//
//       if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
//           System.out.println("Error sending notification");
//           throw new IllegalArgumentException("Notification service is unavailable");
//       }

        System.out.println("notification sent to user");
    }
}
