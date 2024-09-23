package code.acadConecta.model.util;


public class TokenUtil {
    //local de uso da plataforma (desktop ou web)
    private static int registerPlatform = 0;

    //canal que está sendo renderizado na plataforma (EDITAL, VAGAS ou CHAT)
    private static Long currentChanel = 0L;

    //armazena informações do usuário que está atualmente logado
    private static String currentUser;

    //armazena o email descriptografado do usuário
    private static String currentuserEmail;


    public static int getRegisterPlatform() {
        return registerPlatform;
    }

    public static void setRegisterPlatform(int registerPlatform) {
        TokenUtil.registerPlatform = registerPlatform;
    }

    public static Long getCurrentChanel() {
        return currentChanel;
    }

    public static void setCurrentChanel(Long currentChanel) {
        TokenUtil.currentChanel = currentChanel;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        TokenUtil.currentUser = currentUser;
    }

    public static String getCurrentuserEmail() {
        return currentuserEmail;
    }

    public static void setCurrentuserEmail(String currentuserEmail) {
        TokenUtil.currentuserEmail = currentuserEmail;
    }
}
