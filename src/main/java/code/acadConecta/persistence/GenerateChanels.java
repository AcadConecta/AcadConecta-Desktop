package code.acadConecta.persistence;

import code.acadConecta.model.entites.Chanel;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GenerateChanels {

    //retorna a lista de canais da plataforma para o povoamento
    public static List<Chanel> generate() {
        return Arrays.asList(
            new Chanel(null, "EDITAIS", "Canal destinado à divulgação dos editais relacionados com o IFPB-CZ"),
            new Chanel(null, "VAGAS", "Canal destinado à publicação de vagas de emprego e/ou estágio"),
            new Chanel(null, "CHAT", "Canal destinado ao compartilhamento de ideias e conteúdo entre os alunos")
        );
    }
}
