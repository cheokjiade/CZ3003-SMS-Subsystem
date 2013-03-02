package interfaces;

public interface ClientMessageReceived {
	public static final int SUCCESS = 0;
	public static final int UNABLE_TO_SEND = 1;
	public static final int UNABLE_TO_DELIVER = 2;
	public static final int UNABLE_TO_CONNECT_TO_NETWORK = 3;
	public static final int OTHERS = 4;
	public void onMessageReceived(String msg, int errorCode);

}
