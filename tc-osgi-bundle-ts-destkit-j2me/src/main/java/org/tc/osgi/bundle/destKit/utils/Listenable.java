package destKit.utils;

public interface Listenable {

	public void notifyListeners();

	public void addListener(Listener l);
}
