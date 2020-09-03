package hairrang.component;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import hairrang.dto.Sales;
import hairrang.service.SalesService;
import hairrang.table.GuestOrderInfoTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class OrderDetail extends JPanel implements ActionListener {
	
	private GuestOrderInfoTable table;
	private SalesService salesService = new SalesService();
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton radioWeek;
	private JRadioButton radioMonth;
	private JRadioButton radioDay;
	private JDateChooser afterDate;
	private JDateChooser beforeDate;
	
	private JButton btnSreach;
	

	public OrderDetail() {
		initComponents();

		clearTf();
	}

	private void initComponents() {
		setLayout(null);
		
		JPanel DetailTablePanel = new JPanel();
		DetailTablePanel.setBounds(0, 157, 697, 315);
		add(DetailTablePanel);
		DetailTablePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		DetailTablePanel.add(scrollPane, BorderLayout.CENTER);
		
		table = new GuestOrderInfoTable();
		scrollPane.setViewportView(table);
		
		JLabel lblDate = new JLabel("날  짜 :");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setBounds(120, 30, 62, 21);
		add(lblDate);
		
		beforeDate = new JDateChooser();
		beforeDate.setBounds(194, 30, 125, 21);
		add(beforeDate);
		
		JLabel lblDate2 = new JLabel("~");
		lblDate2.setFont(new Font("굴림", Font.BOLD, 12));
		lblDate2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate2.setBounds(331, 33, 29, 15);
		add(lblDate2);
		
		afterDate = new JDateChooser();
		afterDate.setBounds(372, 30, 125, 21);
		add(afterDate);
		
		radioMonth = new JRadioButton("한달");
		buttonGroup.add(radioMonth);
		radioMonth.setHorizontalAlignment(SwingConstants.CENTER);
		radioMonth.setBounds(416, 76, 81, 23);
		radioMonth.addActionListener(acitonLIstener);
		add(radioMonth);
		
		radioWeek = new JRadioButton("일주일");
		radioWeek.setSelected(true);
		buttonGroup.add(radioWeek);
		radioWeek.setHorizontalAlignment(SwingConstants.CENTER);
		radioWeek.setBounds(302, 76, 81, 23);
		radioWeek.addActionListener(acitonLIstener);
		add(radioWeek);
		
		radioDay = new JRadioButton("하루");
		buttonGroup.add(radioDay);
		radioDay.setHorizontalAlignment(SwingConstants.CENTER);
		radioDay.setBounds(194, 76, 81, 23);
		radioDay.addActionListener(acitonLIstener);
		add(radioDay);
		
		btnSreach = new JButton("검색");
		btnSreach.addActionListener(acitonLIstener);
		btnSreach.setBounds(505, 76, 57, 23);
		add(btnSreach);
	}
	
	ActionListener acitonLIstener = new ActionListener() {
		Calendar beforecal = Calendar.getInstance();
		Calendar aftercal = Calendar.getInstance();
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == radioMonth) {
				setPeriod(Calendar.MONTH, -1);
				return;
			}
			if(e.getSource() == radioWeek) {
				setPeriod(Calendar.DATE, -7);
				return;
			}
			if(e.getSource() == radioDay) {
				setPeriod(Calendar.DATE, -1);
				return;
			}
			if(e.getSource() == btnSreach) {
				changeTable();
			}
		}
		
		private void setPeriod(int field, int amount ) {
			beforecal.add(Calendar.MONTH, -1);
			
			beforeDate.setDate(beforecal.getTime());
			afterDate.setDate(aftercal.getTime());
			
			beforecal = Calendar.getInstance();
			aftercal = Calendar.getInstance();
		}
	};
	private Calendar beforecal;
	private Calendar aftercal;
	
	
	
	private void changeTable() {
		try {
			List<Sales> list = new ArrayList<Sales>();
			list = salesService.selectSalesByDate(beforeDate.getDate(), afterDate.getDate());
			table.setItems(list);
		}catch (NullPointerException e) {
			table.setItems(salesService.selectSalesByAll());
		}
	}
	
	public void clearTf() {
		setPeriod(Calendar.DATE, -7);
		changeTable();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == radioMonth) {
			setPeriod(Calendar.MONTH, -1);
			return;
		}
		if(e.getSource() == radioWeek) {
			setPeriod(Calendar.DATE, -7);
			return;
		}
		if(e.getSource() == radioDay) {
			setPeriod(Calendar.DATE, -1);
			return;
		}
		if(e.getSource() == btnSreach) {
			changeTable();
		}
	}
		
	
	private void setPeriod(int field, int amount ) {
		beforecal = Calendar.getInstance();
		aftercal = Calendar.getInstance();
		
		beforecal.add(Calendar.MONTH, -1);
		
		beforeDate.setDate(beforecal.getTime());
		afterDate.setDate(aftercal.getTime());
		
	}
	
}