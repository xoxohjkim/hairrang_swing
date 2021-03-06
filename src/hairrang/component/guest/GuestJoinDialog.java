package hairrang.component.guest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class GuestJoinDialog extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnAdd;
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



	
	public GuestJoinDialog() {
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
		Date date = new Date();
		tfJoinDay.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
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
		tfPhone.setText("ex)010-1234-5678");
		tfPhone.setBounds(134, 235, Configuration.DIM_TF.width, Configuration.DIM_TF.height);
		tfPhone.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				tfPhone.setText("");
				
				
			}
		});
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

		btnAdd = new JButton("??????");
		btnAdd.setBounds(112, 10, 65, 25);
		btnAdd.setActionCommand("OK");
		btnAdd.addActionListener(this);
		buttonPane.add(btnAdd);
		getRootPane().setDefaultButton(btnAdd);

		btnCancel = new JButton("??????");
		btnCancel.setBounds(185, 10, 65, 25);
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);
		buttonPane.add(btnCancel);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAdd) {
			btnAddActionPerformed(e);
		}
		if(e.getSource() == btnCancel) {
			btnCancelActionPerformed(e);
		}
	}
	
	

	private void btnAddActionPerformed(ActionEvent e) {
		Guest addGuest;
		try {
			addGuest = getGuest();
			System.out.println(addGuest);
			gService.addGuest(addGuest);
			//???????????? ??????
			curr++;

			JOptionPane.showMessageDialog(null, String.format("%s?????? ?????????????????????.", addGuest.getGuestName()));
			
			
			guestSearch.listUpdate();
			
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		clearTf();
		setTfNo(curr);
		
	}

	private void btnCancelActionPerformed(ActionEvent e) {
		int result = JOptionPane.showConfirmDialog(null,"?????? ????????? ?????????????????????????", "?????? ??????",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			dispose();
		}else {
			return;
		}
		
		
		
	}

	public void setTfNo(int curr) {
		tfNo.setText(String.valueOf(curr));

	}
	
	public Guest getGuest() throws ParseException {
		isEmpty();
		isValidTf();
		
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
	
	private void isEmpty() {
		String error = "";
		if(tfName.getText().isEmpty()) {
			error= "?????????";
			JOptionPane.showMessageDialog(null, String.format("%s??? ???????????????.",error));
			throw new EmptyTfException("????????????");
		}
		if(dateChooser.getDate() == null) {
			error="????????????";
			JOptionPane.showMessageDialog(null, String.format("%s??? ???????????????.",error));
			throw new EmptyTfException("????????????");
		}
		if(buttonGroup.isSelected(null)) {
			error = "??????";
			JOptionPane.showMessageDialog(null, String.format("%s??? ???????????????.",error));
			throw new EmptyTfException("????????????");
		}
		if(tfPhone.getText().isEmpty()) {
			error = "?????????";
			JOptionPane.showMessageDialog(null, String.format("%s??? ???????????????.",error));
			throw new EmptyTfException("????????????");
		}
		
		
		setTfNo(curr);
		
		
	}
	
	void isValidTf() {
		String name = tfName.getText().trim();
		String phone = tfPhone.getText().trim();
		
		boolean nameCheck = Pattern.matches("^[???-???a-zA-Z]+$", name);
		boolean phoneCheck = Pattern.matches("^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", phone);
		
		//return nameCheck && phoneCheck;
		
		if(!nameCheck) {
			JOptionPane.showMessageDialog(null, "???????????? ????????? ????????? ?????????????????????.");
			throw new InValidationException("????????????");
		}
		if(!phoneCheck) {
			JOptionPane.showMessageDialog(null, "???????????? 010-1234-5678 ???????????? ???????????????.");
			throw new InValidationException("????????????");
		}
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
