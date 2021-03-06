package hairrang.component.guest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import com.toedter.calendar.JDateChooser;

import hairrang.Configuration;
import hairrang.component.GuestSearchView;
import hairrang.dto.Guest;
import hairrang.exception.EmptyTfException;
import hairrang.exception.InValidationException;
import hairrang.service.GuestService;

import javax.swing.ButtonGroup;
import java.awt.event.ActionEvent;

public class GuestUpdateDialog extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnUpdate;
	private JButton btnCancel;
	private JTextField tfName;
	private JTextField tfNo;
	private JTextField tfJoinDay;
	private JTextField tfPhone;
	private JTextField tfMemo;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private ArrayList<Guest> guestList;
	private GuestService gService;
	private int curr;
	private JRadioButton rBtnFemale;
	private JRadioButton rBtnMale;
	private JDateChooser dateChooser;
	private GuestSearchView guestSearch;

	public GuestUpdateDialog() {
		gService = new GuestService();
		guestList = (ArrayList<Guest>) gService.getGuestList();
		curr = gService.getGuestCurrVal();

		initComponent();

	}

	private void initComponent() {

		setBounds(100, 100, 441, 444);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 370, 359);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblName = new JLabel("???  ???  ??? : ");
		lblName.setBounds(59, 75, 70, 15);
		contentPanel.add(lblName);

		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(144, 75, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		contentPanel.add(tfName);

		JLabel lblNo = new JLabel("?????? ?????? : ");
		lblNo.setBounds(59, 35, 70, 15);
		contentPanel.add(lblNo);

		tfNo = new JTextField();
		tfNo.setEditable(false);
		tfNo.setColumns(10);
		setTfNo(curr);
		tfNo.setBounds(144, 35, Configuration.DIM_TF.width, Configuration.DIM_TF.height);

		contentPanel.add(tfNo);

		JLabel lblBirthday = new JLabel("????????????  : ");
		lblBirthday.setBounds(59, 115, 70, 15);
		contentPanel.add(lblBirthday);

		JLabel lblJoinDay = new JLabel("????????????  : ");
		lblJoinDay.setBounds(59, 155, 70, 15);
		contentPanel.add(lblJoinDay);

		tfJoinDay = new JTextField();
		tfJoinDay.setText("2020-09-01");
		tfJoinDay.setEditable(false);
		tfJoinDay.setColumns(10);
		tfJoinDay.setBounds(144, 155, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		contentPanel.add(tfJoinDay);

		JLabel lblGender = new JLabel("???       ??? : ");
		lblGender.setBounds(59, 195, 70, 15);
		contentPanel.add(lblGender);

		JLabel lblPhone = new JLabel("???  ???  ??? : ");
		lblPhone.setBounds(59, 235, 70, 15);
		contentPanel.add(lblPhone);

		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(134, 235, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		contentPanel.add(tfPhone);

		JLabel lblNote = new JLabel("???      ???  : ");
		lblNote.setBounds(59, 275, 70, 15);
		contentPanel.add(lblNote);

		rBtnFemale = new JRadioButton("??????");
		buttonGroup.add(rBtnFemale);
		rBtnFemale.setBounds(137, 190, 60, 23);
		contentPanel.add(rBtnFemale);

		rBtnMale = new JRadioButton("??????");
		buttonGroup.add(rBtnMale);
		rBtnMale.setBounds(201, 190, 60, 23);
		contentPanel.add(rBtnMale);

		tfMemo = new JTextField();
		tfMemo.setColumns(10);
		tfMemo.setBounds(134, 275, Configuration.DIM_TF.width, 60);
		contentPanel.add(tfMemo);

		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(144, 115, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		contentPanel.add(dateChooser);

		buttonPane = new JPanel();
		buttonPane.setBounds(0, 360, 370, 43);
		getContentPane().add(buttonPane);
		buttonPane.setLayout(null);

		btnUpdate = new JButton("??????");
		btnUpdate.setBounds(124, 11, 65, 25);
		btnUpdate.setActionCommand("OK");
		btnUpdate.addActionListener(this);
		buttonPane.add(btnUpdate);
		getRootPane().setDefaultButton(btnUpdate);

		btnCancel = new JButton("??????");
		btnCancel.setBounds(197, 11, 65, 25);
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);
		buttonPane.add(btnCancel);
	}
	
	
//??????////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdate) {
			try {
				btnUpdateActionPerformed(e);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == btnCancel) {
			btnCancelActionPerformed(e);
		}
	}

	private void btnUpdateActionPerformed(ActionEvent e) throws ParseException {
		guestList = (ArrayList<Guest>) gService.getGuestList();
		// ????????? ??????????????? ????????? ??????

		Guest updateInfo = getGuest();
		Guest selectGuest = guestSearch.getSelectedGuest();

		//System.out.println("?????????" + selectGuest);

		// ??????????????? ???????????? ?????????????????????

		int result = JOptionPane.showConfirmDialog(null, "?????? ????????? ?????????????????????????", "?????? ??????", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			
			int guestNo = selectGuest.getGuestNo();
			gService.selectGuestByNo(new Guest(guestNo));
			selectGuest.setGuestName(updateInfo.getGuestName());
			selectGuest.setBirthday(updateInfo.getBirthday());
			selectGuest.setGender(updateInfo.getGender());
			selectGuest.setPhone(updateInfo.getPhone());
			selectGuest.setGuestNote(updateInfo.getGuestNote());
			
			gService.updateGuest(selectGuest);
			//System.out.println("?????????" + selectGuest);
			JOptionPane.showMessageDialog(null, String.format("%s?????? ????????? ?????????????????????.",selectGuest.getGuestName()));
		} else {
			return;
		}
		guestSearch.listUpdate();
		dispose();
		
	}

	
	
	private void btnCancelActionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null, "?????? ?????? ????????? ?????????????????????????", "?????? ??????", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			dispose();
		} else {
			return;
		}

	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setTfNo(int curr) {
		tfNo.setText(String.valueOf(curr));

	}

	public Guest getGuest() throws ParseException {
		isEmpty();

		if (!isValidTf()) {
			JOptionPane.showMessageDialog(null, "????????? ?????? ????????????.");
			throw new InValidationException("????????????");

		}

		Calendar c = Calendar.getInstance();
		Date join = new Date(c.getTimeInMillis());

		// string->date
		int guestNo = Integer.parseInt(tfNo.getText().trim());
		String guestName = tfName.getText().trim();
		Date birthday = dateChooser.getDate();
		Date joinDay = join;
		String phone = tfPhone.getText().trim();
		int gender = rBtnFemale.isSelected() ? 1 : 2;
		String guestNote = tfMemo.getText().trim();
		return new Guest(guestNo, guestName, birthday, joinDay, phone, gender, guestNote);

	}

	public void setGuest(Guest guest) {
		tfNo.setText(String.valueOf(guest.getGuestNo()));
		tfName.setText(guest.getGuestName());
		tfJoinDay.setText(new SimpleDateFormat("yyyy-MM-dd").format(guest.getJoinDay()));
		tfPhone.setText(guest.getPhone());
		dateChooser.setDate(guest.getBirthday());

		if (guest.getGender() == 1) {
			rBtnFemale.setSelected(true);
		} else {
			rBtnMale.setSelected(true);
		}
		tfMemo.setText(guest.getGuestNote());

	}

	private void isEmpty() {
		String error = "";
		if (tfName.getText().isEmpty()) {
			error = "?????????";
			JOptionPane.showMessageDialog(null, String.format("%s??? ???????????????.", error));
			throw new EmptyTfException("????????????");
		}
		if (buttonGroup.isSelected(null)) {
			error = "??????";
			JOptionPane.showMessageDialog(null, String.format("%s??? ???????????????.", error));
			throw new EmptyTfException("????????????");
		}
		if (tfPhone.getText().isEmpty()) {
			error = "?????????";
			JOptionPane.showMessageDialog(null, String.format("%s??? ???????????????.", error));
			throw new EmptyTfException("????????????");
		}

		setTfNo(curr);

	}

	boolean isValidTf() {
		String name = tfName.getText().trim();
		String phone = tfPhone.getText().trim();

		boolean nameCheck = Pattern.matches("^[???-???a-zA-Z]+$", name);
		boolean phoneCheck = Pattern.matches("^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", phone);

		return nameCheck && phoneCheck;
	}

	public void clearTf() {

		tfName.setText("");
		Date date = new Date();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(date);

		tfJoinDay.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
		tfPhone.setText("");
		buttonGroup.clearSelection();
		tfMemo.setText("");

	}

	public GuestSearchView getGuestSearch() {
		return guestSearch;
	}

	public void setGuestSearch(GuestSearchView guestSearch) {
		this.guestSearch = guestSearch;
	}

}
