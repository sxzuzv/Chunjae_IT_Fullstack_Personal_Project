package addr;

import java.util.ArrayList;
import java.util.Scanner;

public class AddrApp {

  public static void main(String[] args) {
		
		DBUtil db = new DBUtil();
		Scanner scan = new Scanner(System.in);

		db.init();

		while(true) {
			System.out.print("명령어를 입력해주세요 : ");
			String cmd = scan.nextLine();
			
			if(cmd.equals("exit")) {
				break;
				
			} else if(cmd.equals("add")) {
				// DB 데이터 생성 기능 (insert)
				System.out.println("========= 주소록 등록 =========");
				System.out.print("이름 : ");
				String name = scan.nextLine();
				System.out.print("주소 : ");
				String address = scan.nextLine();
				System.out.print("전화번호 : ");
				String phone = scan.nextLine();

				// id는 AUTO_INCREMENT로 설정되어 있기 때문에, 데이터가 삽입될 떄 자동으로 id 값이 1 증가되어 삽입된다.
				// 따라서 데이터 삽입 시, java 프로그램에서 id 값을 관리할 필요가 없다.
				db.insertAddress(name, address, phone);
				
				System.out.println("주소록 등록 완료.");
				System.out.println("============================");
				
			} else if(cmd.equals("list")) {
				// DB 전체 검색 기능 (select *)
				ArrayList<Addr> addrList = db.getAddresses();
				WebView wv = new WebView();
				wv.printAddr(addrList);
				
				
			} else if(cmd.equals("update")) {
				// DB 데이터 수정 기능 (update)
				System.out.print("몇번 주소록을 수정하시겠습니까 : ");
				int id = Integer.parseInt(scan.nextLine()); 
				System.out.println("========= 주소록 수정 =========");
				System.out.print("이름 : ");
				String name = scan.nextLine();
				System.out.print("주소 : ");
				String address = scan.nextLine();
				System.out.print("전화번호 : ");
				String phone = scan.nextLine();
				
				db.updateAddress(id, name, address, phone);
				
				System.out.println("주소록 수정 완료.");
				System.out.println("============================");
				
			} else if(cmd.equals("delete")) {
				// DB 데이터 삭제 기능 (delete from)
				System.out.print("몇번 주소록을 삭제하시겠습니까 : ");
				int id = Integer.parseInt(scan.nextLine());
				db.deleteAddress(id);
				System.out.println(id + "번 주소록이 삭제되었습니다.");
				System.out.println("==============================");
			}
		}	
	}
}
