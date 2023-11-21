package addr;

import java.util.ArrayList;

public interface AddrMapper {

	// public 리턴타입 메서드명 (파라미터);
	public ArrayList<Addr> getAddresses();
	public void insertAddress(Addr addr);
	public void updateAddress(Addr addr);
	public void deleteAddress(int id);
}
