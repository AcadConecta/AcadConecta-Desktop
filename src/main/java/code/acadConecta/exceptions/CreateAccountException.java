package code.acadConecta.exceptions;

public class CreateAccountException extends Exception{

    //exception personalizada para tratar erros envolvendo a criação usuários
    public CreateAccountException(String message) {
        super(message);
    }
}
