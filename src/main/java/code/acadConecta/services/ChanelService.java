package code.acadConecta.services;

import code.acadConecta.model.entites.Chanel;
import code.acadConecta.repositories.ChanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChanelService {

    @Autowired
    private ChanelRepository chanelRepository;

    public Chanel findById(Long id) {
        Optional<Chanel> optionalChanel = chanelRepository.findById(id);

        return optionalChanel.orElse(null);
    }

    public List<Chanel> findAll() {
        return chanelRepository.findAll();
    }

    public void save(Chanel chanel) {

        try {
            if (chanel == null) {
                throw new NullPointerException();
            }
            chanelRepository.save(chanel);
        } catch (NullPointerException error) {
            System.out.println("Error in add chanel on database: " + error.getMessage());
        }
    }

}
