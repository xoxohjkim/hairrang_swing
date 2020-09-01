package hairrang.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.IDateEvaluator;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import hairrang.Configuration;
import hairrang.dto.Guest;
import hairrang.exception.EmptyTfException;
import hairrang.exception.InValidationException;
import hairrang.service.GuestService;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GuestManagementPanel extends JPanel {
	private JTextField tfNo;
	private JTextField tfName;
	private JTextField tfJoinDay;
	private JTextField tfPhone;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rBtnFemale;
	private JRadioButton rBtnMale;
	private JTextField tfMemo;
	private GuestService gService;
	private JDateChooser dateChooser;
	private ArrayList<Guest> guestList;
	private int curr;
	

	/**
	 * Create the panel.
	 */
	public GuestManagementPanel() {
		setBackground(Color.WHITE);
		gService = new GuestService();
		guestList = (ArrayList<Guest>) gService.getGuestList();
		curr = gService.getGuestCurrVal();
		
		initComponents();

	}

	private void initComponents() {
		
		
		setLayout(null);

		JLabel lblNo = new JLabel("고객 번호 : ");
		lblNo.setBounds(100, 33, 70, 15);
		add(lblNo);

		tfNo = new JTextField();
		tfNo.setBounds(185, 30, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		add(tfNo);
		tfNo.setColumns(10);
		tfNo.setEditable(false);

		JLabel lblName = new JLabel("고  객  명 : ");
		lblName.setBounds(100, 70, 70, 15);
		add(lblName);

		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(185, 65, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		add(tfName);

		JLabel lblBirthday = new JLabel("생년월일  : ");
		lblBirthday.setBounds(100, 105, 70, 15);
		add(lblBirthday);

		JLabel lblJoinDay = new JLabel("가입일자  : ");
		lblJoinDay.setBounds(100, 138, 70, 15);

		add(lblJoinDay);

		tfJoinDay = new JTextField();
		tfJoinDay.setColumns(10);
		tfJoinDay.setBounds(185, 135, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		tfJoinDay.setEditable(false);
		add(tfJoinDay);

		JLabel lblGender = new JLabel("성       별 : ");
		lblGender.setBounds(400, 31, 70, 15);
		add(lblGender);

		JLabel lblPhone = new JLabel("연  락  처 : ");
		lblPhone.setBounds(400, 68, 70, 15);
		add(lblPhone);

		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(478, 65, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		add(tfPhone);

		JLabel lblMemo = new JLabel("메      모  : ");
		lblMemo.setBounds(400, 105, 70, 15);
		add(lblMemo);

		rBtnFemale = new JRadioButton("여성");
		rBtnFemale.setBackground(Color.WHITE);
		buttonGroup.add(rBtnFemale);
		rBtnFemale.setBounds(478, 30, 60, 23);
		add(rBtnFemale);

		rBtnMale = new JRadioButton("남성");
		buttonGroup.add(rBtnMale);
		rBtnMale.setBounds(542, 30, 60, 23);
		add(rBtnMale);

		tfMemo = new JTextField();
		tfMemo.setBounds(478, 102, Configuration.DIM_TF.width, 60);
		add(tfMemo);
		tfMemo.setColumns(10);

		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		Date date = new Date();
		dateChooser.setDate(date);
		dateChooser.setBounds(185, 99, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		add(dateChooser);
				
	
	}

	public Guest getGuest() throws ParseException {
		isEmpty();
		
		if(!isValidTf()) {
			JOptionPane.showMessageDialog(null, "형식ㄴㄴ");
			throw new InValidationException("형식오류");
			
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

	// 패널셋 date->string
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

	public void setTfNo(JTextField tfNo) {
		this.tfNo = tfNo;
		
	}

	// 오늘날짜 셋
	public void setTfJoinDay() {
		Date date = new Date();
		tfJoinDay.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
	}

	public void setTfNo(int curr) {
		tfNo.setText(String.valueOf(curr));

	}
	
	public String tfNo() {
		return String.valueOf(tfNo.getText().trim());
	}
	
	private void isEmpty() {
		String error = "";
		if(tfName.getText().isEmpty()) {
			error= "고객명";
			JOptionPane.showMessageDialog(null, String.format("%s을 입력하세요.",error));
			throw new EmptyTfException("공란존재");
		}
		if(buttonGroup.isSelected(null)) {
			error = "성별";
			JOptionPane.showMessageDialog(null, String.format("%s을 선택하세요.",error));
			throw new EmptyTfException("공란존재");
		}
		if(tfPhone.getText().isEmpty()) {
			error = "연락처";
			JOptionPane.showMessageDialog(null, String.format("%s를 입력하세요.",error));
			throw new EmptyTfException("공란존재");
		}
		
		setTfNo(curr);
		
	}
	
	boolean isValidTf() {
		String name = tfName.getText().trim();
		String phone = tfPhone.getText().trim();
		
		boolean nameCheck = Pattern.matches("^[가-힣a-zA-Z]+$", name);
		boolean phoneCheck = Pattern.matches("^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", phone);
		
		return nameCheck && phoneCheck;
	}
}
