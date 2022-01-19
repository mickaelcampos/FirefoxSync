/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author m1k4
 */
public final class SyncServer {
    
    private static SyncServer instance = null;
    
    public static SyncServer getInstance() {
        if (instance == null) {
            instance = new SyncServer();
        }
        return instance;
    }
    
    public boolean signUp(String username, String authenticationToken) {
        // TODO verificar se ja existe username, se existir, return false e motivo
        SCRYPTUtil SCRYPTUtil = new SCRYPTUtil();
        String hashedAuthToken = SCRYPTUtil.getHashedAuthToken(authenticationToken);
        // TODO armazenar hashedAuthToken/username em arquivo usando criptografia autenticada                
        return true;
    }
}
