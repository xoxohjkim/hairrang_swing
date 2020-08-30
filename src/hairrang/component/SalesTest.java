package hairrang.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import hairrang.dto.Hair;
import hairrang.dto.Sales;
import hairrang.service.SalesService;
import hairrang.table.HairItemTable;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class SalesTest extends JPanel {
	private ArrayList<Hair> list = new ArrayList<Hair>();
	private HairItemTable table;
	private JTextField tfSumPrice;
	private JTextField tfTotalPrice;
	private JButton btnOrder;
	private JButton btnGuestSreach;
	private JButton btnCancel;
	private SalesOrderPanel salesPanel;
	private SalesService salesService = new SalesService();

	/**
	 * Create the panel.
	 */
	public SalesTest() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 629, 219);
		add(panel);
		panel.setLayout(null);

		salesPanel = new SalesOrderPanel();
		salesPanel.setBounds(0, 0, 437, 219);
		panel.add(salesPanel);

		JPanel hairTable = new JPanel();
		hairTable.setBounds(438, 0, 191, 219);
		panel.add(hairTable);
		hairTable.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		hairTable.add(scrollPane, BorderLayout.CENTER);

		table = new HairItemTable();
		table.setItems(list);
		table.setComponentPopupMenu(createPopMenu());
		scrollPane.setViewportView(table);

		salesPanel.setHtable(table);

		JLabel lblSumPrice = new JLabel("합계 :");
		lblSumPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumPrice.setBounds(444, 229, 57, 21);
		add(lblSumPrice);

		tfSumPrice = new JTextField();
		tfSumPrice.setBounds(501, 229, 116, 21);
		salesPanel.setTfSumPrice(tfSumPrice);
		add(tfSumPrice);
		tfSumPrice.setColumns(10);

		JLabel lblTotalPrice = new JLabel("총계 :");
		lblTotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPrice.setBounds(444, 260, 57, 21);
		add(lblTotalPrice);

		tfTotalPrice = new JTextField();
		tfTotalPrice.setColumns(10);
		tfTotalPrice.setBounds(501, 260, 116, 21);
		salesPanel.setTfTotalPrice(tfTotalPrice);
		add(tfTotalPrice);

		btnOrder = new JButton("주문");
		btnOrder.addActionListener(actionlistener);
		btnOrder.setBounds(523, 291, 94, 23);
		add(btnOrder);

		btnGuestSreach = new JButton("고객검색");
		btnGuestSreach.addActionListener(actionlistener);
		btnGuestSreach.setBounds(417, 291, 94, 23);
		add(btnGuestSreach);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(actionlistener);
		btnCancel.setBounds(311, 291, 94, 23);
		add(btnCancel);

	}

	ActionListener actionlistener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnCancel) {
				btnCancelAction();
			}
			if (e.getSource() == btnGuestSreach) {

			}
			if (e.getSource() == btnOrder) {
				btnOrderAction();
				
			}
			if (e.getActionCommand().equals("상품삭제")) {
				deleteHairItem(e);
			}

		}

		private void btnOrderAction() {
			System.out.println(salesPanel.getSales());
			for(Sales sales : salesPanel.getSales()) {
				salesService.insertSales(sales);
			}
			salesPanel.clearTf();
			
			
			
		}

	};

	public JPopupMenu createPopMenu() {
		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem deleteMenu = new JMenuItem("상품삭제");
		popMenu.add(deleteMenu);
		deleteMenu.addActionListener(actionlistener);
		return popMenu;
	}

	private void btnCancelAction() {
		System.out.println("액션시작");
		salesPanel.clearTf();
		table.setCount(0);

	}

	private void deleteHairItem(ActionEvent e) {
		int selectIndex = table.getSelectedRow();
		if (selectIndex == -1) {
			return;
		}
		System.out.println(selectIndex);
		Hair selectHair = table.getSelectedRow(selectIndex);
		table.removeRow(selectIndex);
		salesPanel.subSumTotal(selectHair);
	}


}
