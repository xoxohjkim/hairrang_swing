package hairrang.component;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.activation.MailcapCommandMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import hairrang.Configuration;
import hairrang.HairshopManagementProgram;
import hairrang.component.guest.GuestJoinDialog;
import hairrang.component.guest.GuestOrderInfoDialog;
import hairrang.component.guest.GuestSearchPanel;
import hairrang.component.guest.GuestUpdateDialog;
import hairrang.dto.Guest;
import hairrang.dto.Sales;
import hairrang.service.GuestService;
import hairrang.service.SalesService;
import hairrang.table.GuestSearchTable;

public class GuestSearchView extends JPanel implements ActionListener {
	private GuestSearchPanel pGuest;
	private JPanel pBtn;
	private JButton btnJoin;
	private JButton btnInfo;
	private JButton btnOrder;
	private JPanel pTable;
	private JScrollPane scrollPane;
	private GuestSearchTable table;
	private GuestService gService;
	private SalesService sService;
	private ArrayList<Guest> guestList;
	private ArrayList<Sales> salesList;
	private GuestOrderInfoDialog orderInfo;
	
	private GuestJoinDialog joinDialog;
	private GuestUpdateDialog UpdateDialog;
	private HairshopManagementProgram program;

	public GuestSearchView() {
		gService = new GuestService();
		guestList = (ArrayList<Guest>) gService.getGuestList();

		sService = new SalesService();
		salesList = (ArrayList<Sales>) sService.selectSalesByAll();

		initComponents();
	}

	private void initComponents() {
		setLayout(null);

		pGuest = new GuestSearchPanel();
		pGuest.setBounds(0, 0, 700, 61);
		add(pGuest);
		pGuest.setMainFrame(this);

		pBtn = new JPanel();
		pBtn.setBounds(0, 61, 700, 61);
		add(pBtn);
		pBtn.setLayout(null);

		btnJoin = new JButton("?????? ??????");
		btnJoin.setBounds(190, 20, Configuration.DIM_BTN.width, Configuration.DIM_BTN.height);
		btnJoin.addActionListener(this);
		pBtn.add(btnJoin);

		btnInfo = new JButton("?????? ??????");
		btnInfo.setBounds(300, 20, Configuration.DIM_BTN.width, Configuration.DIM_BTN.height);
		btnInfo.addActionListener(this);
		pBtn.add(btnInfo);

		btnOrder = new JButton("??????");
		btnOrder.setBounds(410, 20, Configuration.DIM_BTN.width, Configuration.DIM_BTN.height);
		btnOrder.addActionListener(this);
		pBtn.add(btnOrder);

		pTable = new JPanel();
		pTable.setBounds(0, 125, 700, 415);
		add(pTable);
		pTable.setLayout(new GridLayout(1, 0, 0, 0));

		scrollPane = new JScrollPane();
		pTable.add(scrollPane);

		table = new GuestSearchTable();
		scrollPane.setViewportView(table);

		guestList = (ArrayList<Guest>) gService.getGuestList();
		table.setItems(guestList);
		table.setComponentPopupMenu(createPopMenu());
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			int col = table.columnAtPoint(e.getPoint());
			int row = table.rowAtPoint(e.getPoint());
			table.changeSelection(row, col, false, false);
		}
	}

	public GuestOrderInfoDialog getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(GuestOrderInfoDialog orderInfo) {
		this.orderInfo = orderInfo;
	}

	public HairshopManagementProgram getProgram() {
		return program;
	}

	public void setProgram(HairshopManagementProgram program) {
		this.program = program;
	}

	public Guest getSelectedGuest() {
		int selectedRow = table.getSelectedRow();
		int no = (int) table.getValueAt(selectedRow, 0);
		Guest guest = gService.selectGuestByNo(new Guest(no));
		// pGuest.setGuest(guest);
		return guest;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnJoin) {
			btnJoinActionPerformed(e);
		}
		if (e.getSource() == btnInfo) {
			btnInfoActionPerformed(e);
		}

		if (e.getSource() == btnOrder) {
			btnOrderActionPerformed(e);
		}
	}

	private void btnJoinActionPerformed(ActionEvent e) {

		joinDialog = new GuestJoinDialog();
		joinDialog.setBounds(750, 300, 370, 500);
		joinDialog.setTitle("?????? ??????");
		joinDialog.setVisible(true);
		joinDialog.setGuestSearch(this);

	}

//
//	private void btnCancelActionPerformed(ActionEvent e) {
//		pGuest.clearTf();
//		guestList = (ArrayList<Guest>) gService.getGuestList();
//		table.setItems(guestList);
//
//	}

	private void btnInfoActionPerformed(ActionEvent e) {
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "????????? ???????????????");
			return;
		}

		orderInfo = new GuestOrderInfoDialog();
		orderInfo.setBounds(500, 550, 515, 480);
		orderInfo.setTitle("????????????");
		orderInfo.setVisible(true);
		orderInfo.setLocationRelativeTo(program);

		// String guestName = pGuest.tfName().trim();
		// String guestNo = pGuest.tfNo().trim();

		Guest guest = getSelectedGuest();
		int guestNo = guest.getGuestNo();
		String guestName = guest.getGuestName();
		orderInfo.selectGuest(guestNo, guestName);

	}

	// ???????????????????????? ??????????????????
	private void btnOrderActionPerformed(ActionEvent e) {
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "????????? ???????????????");
			return;
		}

		Guest guest = getSelectedGuest();

		int no = guest.getGuestNo();
		String name = guest.getGuestName();

//		program.switchPanel(1);
		program.switchToSalesView();
		program.getP3().getSalesPanel().setGuest(no, name);

	}

	public void searchGuestName(String search) {
		ArrayList<Guest> result = (ArrayList<Guest>) gService.searchGuestByName(new Guest(search));
		// result.stream().forEach(System.out::println);
		table.setItems(result);

	}

	public void searchGuestBirthday(String search) {
		ArrayList<Guest> result = (ArrayList<Guest>) gService.searchGuestByBirthday(search);
		// result.stream().forEach(System.out::println);
		table.setItems(result);
	}

	public void searchGuestPhone(String search) {
		ArrayList<Guest> result = (ArrayList<Guest>) gService.searchGuestByPhone(search);
		// result.stream().forEach(System.out::println);
		table.setItems(result);

	}

	public void listUpdate() { // ????????????
		guestList = (ArrayList<Guest>) gService.getGuestList();
		table.setItems(guestList);
	}

// ?????????//////////////////////////////////////////////////////////////////////////////////////////////////////////

	public JPopupMenu createPopMenu() {
		JPopupMenu popMenu = new JPopupMenu();
		
		popMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popMenu, new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

		

		JMenuItem updateMenu = new JMenuItem("?????? ?????? ??????");
		JMenuItem deleteMenu = new JMenuItem("?????? ?????? ??????");

		popMenu.add(updateMenu);
		popMenu.add(deleteMenu);

		updateMenu.addActionListener(addActionlistener);
		deleteMenu.addActionListener(addActionlistener);

		return popMenu;
	}

	ActionListener addActionlistener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("?????? ?????? ??????")) {
				try {
					actionUpdate();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getActionCommand().equals("?????? ?????? ??????")) {
				actionDelete();
			}

		}
	};

	// ??????????????? ????????????
	protected void actionUpdate() throws ParseException {
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "????????? ???????????????");
			return;
		}

		// ????????? ???????????? ??????
		Guest update = getSelectedGuest();

		UpdateDialog = new GuestUpdateDialog();
		UpdateDialog.setBounds(750, 300, 370, 500);
		UpdateDialog.setTitle("?????? ?????? ??????");
		UpdateDialog.setVisible(true);
		UpdateDialog.setGuestSearch(this);

		UpdateDialog.setGuest(update);

	}

	protected void actionDelete() {
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(null, "????????? ???????????????");
			return;
		}

		int idx = table.getSelectedRow();
		Guest delete = getSelectedGuest();

		int result = JOptionPane.showConfirmDialog(null, String.format("%s?????? ?????????????????????????", delete.getGuestName()), "??????",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			gService.deleteGuest(delete);
			table.removeRow(idx);
			pGuest.clearTf();
		} else {
			return;
		}

	}
	
	

}
