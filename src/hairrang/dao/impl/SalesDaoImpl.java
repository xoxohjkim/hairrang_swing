package hairrang.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hairrang.chart.SalesChart;
import hairrang.conn.JdbcUtil;
import hairrang.dao.SalesDao;
import hairrang.dto.Event;
import hairrang.dto.Guest;
import hairrang.dto.Hair;
import hairrang.dto.Sales;

public class SalesDaoImpl implements SalesDao{
	private static final SalesDaoImpl instance = new SalesDaoImpl();
	private SalesChart salesChart;
	private SalesDaoImpl() {};
	  
	public static SalesDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<Sales> selectSalesByAll() {

		String sql = "SELECT * FROM SALES s "
				+ "LEFT OUTER JOIN HAIR h USING (HAIR_NO) "
				+ "LEFT OUTER JOIN GUEST g USING (GUEST_NO) "
				+ "LEFT OUTER JOIN EVENT e USING (EVENT_NO) "
				+ "ORDER BY SALES_NO";
		try(Connection con = JdbcUtil.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()){
			if(rs.next()) {
				List<Sales> list = new ArrayList<Sales>();
				do {
					list.add(getSales(rs));
				}while(rs.next());
				return list;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}
	
	
	

	@Override
	public List<Sales> selectSalesByGuestNo(Sales sales) {
		String sql = "SELECT * FROM SALES s " + 
				"	LEFT OUTER JOIN HAIR h ON (s.HAIR_NO = h.HAIR_NO ) "  + 
				"	LEFT OUTER JOIN GUEST g ON (g.GUEST_NO = s.GUEST_NO) " + 
				"	LEFT OUTER JOIN EVENT e ON (s.EVENT_NO = e.EVENT_NO) " + 
				"	WHERE s.GUEST_NO = ?";
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, sales.getSalesNo());
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					List<Sales> list = new ArrayList<Sales>();
					do {
						list.add(getSales(rs));
					}while(rs.next());
 					return list;
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public int insertSales(Sales sales) {
		String sql = "INSERT INTO SALES VALUES (SALES_SEQ.NEXTVAL,?,?,?,?,?)";
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setTimestamp(1, new java.sql.Timestamp(sales.getSalesDay().getTime()));
			pstmt.setInt(2, sales.getGuestNo().getGuestNo());
			pstmt.setInt(3, sales.getEventNo().getEventNo());
			pstmt.setInt(4, sales.getHairNo().getHairNo());
			pstmt.setInt(5, sales.getTotalPrice());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Sales> selectSalesByDate(Date before, Date after) {
		String sql = "SELECT * FROM SALES s JOIN HAIR h ON (s.HAIR_NO = h.HAIR_NO ) JOIN GUEST g ON (g.GUEST_NO = s.GUEST_NO) JOIN EVENT e ON (s.EVENT_NO = e.EVENT_NO) " +
					" WHERE TO_CHAR(SALES_DAY, 'YYYY-MM-DD') BETWEEN TO_CHAR(?, 'YYYY-MM-DD') AND TO_CHAR(?, 'YYYY-MM-DD') ORDER BY SALES_NO";
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			java.sql.Date beforeDate = new java.sql.Date(before.getTime());
			java.sql.Date afterDate = new java.sql.Date(after.getTime());
			
			pstmt.setDate(1, beforeDate);
			pstmt.setDate(2, afterDate);
			
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) { 
					List<Sales> list = new ArrayList<Sales>();
					do {
						list.add(getSales(rs));
					}while(rs.next());
					return list;
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
		
		
	}
	
	
	@Override
	public List<Sales> selectSalesByDate(int before, int after) {
		String sql = "SELECT * FROM SALES s JOIN HAIR h ON (s.HAIR_NO = h.HAIR_NO ) JOIN GUEST g ON (g.GUEST_NO = s.GUEST_NO) JOIN EVENT e ON (s.EVENT_NO = e.EVENT_NO) "
				+ "WHERE TO_CHAR(SALES_DAY, 'YYYY') BETWEEN ? AND ? ORDER BY SALES_NO";
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
//			java.sql.Date beforeDate = new java.sql.Date(before.getTime());
//			java.sql.Date afterDate = new java.sql.Date(after.getTime());
			
			pstmt.setInt(1, before);
			pstmt.setInt(2, after);
			
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) { 
					List<Sales> list = new ArrayList<Sales>();
					do {
						list.add(getSales(rs));
					}while(rs.next());
					return list;
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
		
		
	}
	
	public Sales getSales(ResultSet rs) throws SQLException {

		/*
		 * int gno = rs.getInt("GUEST_NO"); String gname = rs.getString("GUEST_NAME");
		 * Date bday = rs.getDate("BIRTHDAY"); Date jday = rs.getDate("JOIN_DAY");
		 * String phone = rs.getString("PHONE"); int gender = rs.getInt("GENDER");
		 * String gnote = rs.getString("GUEST_NOTE"); Guest guest = new Guest(gno,
		 * gname, bday, jday, phone, gender, gnote);
		 * 
		 * 
		 * int eno = rs.getInt("EVENT_NO"); String ename = rs.getString("EVENT_NAME");
		 * double sale = rs.getInt("SALE"); Event event = new Event(eno, ename, sale);
		 * 
		 * 
		 * int no = rs.getInt("SALES_NO"); Date salesDay = rs.getDate("SALES_DAY"); Date
		 * salesTime = rs.getDate("SALES_TIME"); Sales sales = new Sales(no, salesDay,
		 * salesTime, guest, event);
		 */
		
		HairDaoImpl hdao = HairDaoImpl.getInstance();
		Hair hair = hdao.getHair(rs);
		
		//?????? ???????????? ?????? ?????? ??????
		
		GuestDaoImpl gdao = GuestDaoImpl.getInstance();
		Guest guestt  = gdao.getGuest(rs);
		
		EventDaoImpl edao = EventDaoImpl.getInstance();
		Event evnett = edao.getEvent(rs);
		
		
		/*
		 * int no = rs.getInt("EVENT_NO"); String name = rs.getString("EVENT_NAME");
		 * float sale = rs.getFloat("SALE");
		 * 
		 * Event event = new Event(no, name, sale);
		 * 
		 * 
		 * GuestDaoImpl gdao = GuestDaoImpl.getInstance(); Guest guest =
		 * gdao.getGuest(rs);
		 */
		
		int sno = rs.getInt("SALES_NO");
		Date day = rs.getDate("SALES_DAY");
		int totalPrice = rs.getInt("TOTAL_PRICE");
		
		Sales sales = new Sales(sno, day, guestt, evnett, hair,totalPrice);
		
		return sales;
	}

	
	public int getTodaySalesCount() {
		
		String sql = "SELECT count(*) AS TODAY_COUNT FROM SALES WHERE TO_CHAR(SALES_DAY, 'yyyy-mm-dd') = TO_CHAR(SYSDATE, 'yyyy-mm-dd')";
		
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			if(rs.next()) {
				return rs.getInt("TODAY_COUNT");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return 0;
	}

	public int sequencesLastNumber() {
		String sql = "SELECT LAST_NUMBER FROM user_sequences WHERE SEQUENCE_NAME = UPPER('SALES_SEQ')";
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery()){
			if(rs.next()) {
				return rs.getInt("LAST_NUMBER");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return 0;
	}
	
	
	@Override
	public List<int[]> selectSalesByYearForChart (int startYear, int endYear) {
		
		  String sql =
			  "SELECT TO_CHAR(SALES_DAY, 'YYYY') YEAR, SUM(TOTAL_PRICE) SUM "
			  + " FROM SALES " + " GROUP BY TO_CHAR(SALES_DAY, 'YYYY') " +
			  " HAVING to_char(sales_day, 'YYYY') BETWEEN ? AND ? ORDER BY YEAR ASC";
		  
			try (Connection con = JdbcUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setInt(1, startYear);
				pstmt.setInt(2, endYear);
				
				try (ResultSet rs = pstmt.executeQuery()) {
					
					List<int[]> list = new ArrayList<>();
					
					if (rs.next()) {
						int i = startYear;
						for(i = startYear; i <= endYear; i++) {
							int year = rs.getInt("YEAR");
							if (i != year) {
								list.add(new int[] {i, 0});
								continue;
							}
							
							list.add(new int[] {rs.getInt("YEAR"), rs.getInt("SUM")});
							if (!rs.next()) {
								break;
							}
						}
						
						while(i < endYear) {
							list.add(new int[] {++i, 0});
							System.out.println(i);
						}
					} else {
						// ?????? ???????????? ?????? ??????
						for(int i = startYear; i <= endYear; i++) {
							list.add(new int[] {i, 0});
						}
					}
					return list;
				}

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

	@Override
	public List<int[]> selectSalesByMonthForChart(int selectMonthYear) {
		String sql=" SELECT TO_CHAR(SALES_DAY, 'MM') MONTH , SUM(TOTAL_PRICE ) SUM " + 
				" FROM SALES S " + 
				" WHERE TO_CHAR(SALES_DAY, 'YYYY') = ? " + 
				" GROUP BY TO_CHAR(SALES_DAY, 'MM')"
				+ " ORDER BY MONTH ASC" ;
				
		
		try (Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
		
			pstmt.setInt(1, selectMonthYear);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				List<int[]> list = new ArrayList<>();
				
				if(rs.next()) {
					for(int i = 1; i < 12; i++) {
						int month = rs.getInt("MONTH");
						if (i != month) {
							list.add(new int[] {i, 0});
							continue;
						}
						list.add(new int[] {month, rs.getInt("SUM")});
						
						if (!rs.next()) {
							for (int j = i+1; j <= 12; j++) {
								list.add(new int[] {j, 0});
							}
							break;
						}
					} 
				} else {
					for (int i = 1; i <=12; i++) {
						list.add(new int[] {i, 0});
					}
				}
				return list;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<Sales> selectSalesByMonth(int startMonthYear) {
		String sql = "SELECT * FROM SALES s JOIN HAIR h ON (s.HAIR_NO = h.HAIR_NO ) JOIN GUEST g ON (g.GUEST_NO = s.GUEST_NO) JOIN EVENT e ON (s.EVENT_NO = e.EVENT_NO) "
				+ "WHERE TO_CHAR(SALES_DAY, 'YYYY') = ? " ;
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setInt(1, startMonthYear);
		
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) { 
					List<Sales> list = new ArrayList<Sales>();
					do {
						list.add(getSales(rs));
					}while(rs.next());
					return list;
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
		
		
	}



	@Override
	public Date oldSalesDay() {
		String sql = "SELECT MIN(SALES_DAY ) AS MINDAY FROM SALES";
		try(Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery()){
			if(rs.next()) {
				return rs.getDate("MINDAY");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	

	public int selectSalesMinYear() {
		String sql = "SELECT min(to_char(sales_day, 'YYYY')) AS MIN_YEAR FROM SALES";

		try (Connection con = JdbcUtil.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if(rs.next()) {
				return rs.getInt("MIN_YEAR");
			}
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return 0;
	}
}
	
	/*
	 * @Override public int updateSales(Sales sales) { String sql =
	 * "UPDATE SALES \r\n" + "	SET SALES_DAY = ? ,\r\n" +
	 * "		SALES_TIME  = ? ,\r\n" + "		GUEST_NO = ? ,\r\n" +
	 * "		EVENT_NO = ? \r\n" + "	WHERE SALES_NO =?"; try(Connection con =
	 * JdbcUtil.getConnection(); PreparedStatement pstmt =
	 * con.prepareStatement(sql)){ pstmt.setDate(1, sales.getSalesDay());
	 * pstmt.setDate(2, sales.getSalesTime()); pstmt.setInt(3,
	 * sales.getGuestNo().getGuestNo()); pstmt.setInt(4,
	 * sales.getEventNo().getEventNo()); pstmt.setInt(5, sales.getSalesNo()); return
	 * pstmt.executeUpdate(); } catch (SQLException e) { throw new
	 * RuntimeException(e); } }
	 * 
	 * @Override public int deleteSales(Sales sales) { String sql =
	 * "DELETE SALES WHERE SALES_NO =?"; try(Connection con =
	 * JdbcUtil.getConnection(); PreparedStatement pstmt =
	 * con.prepareStatement(sql)){ pstmt.setInt(1, sales.getSalesNo()); return
	 * pstmt.executeUpdate(); } catch (SQLException e) { throw new
	 * RuntimeException(e); }
	 * 
	 * 
	 * }
	 */


