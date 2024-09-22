package code.acadConecta.persistence;

import code.acadConecta.services.ChanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer  implements CommandLineRunner {

    @Autowired
    private ChanelService chanelService;

    @Override
    public void run(String... args) throws Exception {
    //realiza o povoamento da tabela Chanel no banco ao inicializar a aplicação
        try{
            //verifica se o canal com id 1 existe, se existir, significa que todos os outros existem, e que o povoamento é desnecessário
            if (chanelService.findById(1L) == null) {
                GenerateChanels.generate().forEach(chanel -> {
                    chanelService.save(chanel);
                });
            }

        } catch (NullPointerException err) {
            System.out.println("Error to load chanel in database: " + err.getMessage());
        }
    }
}
