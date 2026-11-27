package vista;

import baseDatos.GestionBD;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class VentanAlum extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAlumno;
	private JTable table;
	private GestionBD gbd = new GestionBD();
	private String alumnoActual;
	private JButton btnAlumVolver;
	private JComboBox<String> cbModulo;
	private JButton btnAlumSalir;

	public VentanAlum() {
		this("");
	}

	public VentanAlum(String alumnoUsuario) {
		this.alumnoActual = alumnoUsuario;
		
		setResizable(false);
		setTitle("VENTANA ALUMNO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAlumSalir = new JButton("SALIR");
		btnAlumSalir.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAlumSalir.addActionListener(this);
		btnAlumSalir.setBounds(439, 234, 88, 29);
		contentPane.add(btnAlumSalir);
		
		btnAlumVolver = new JButton("VOLVER");
		btnAlumVolver.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAlumVolver.addActionListener(this);
		btnAlumVolver.setBounds(332, 234, 88, 28);
		contentPane.add(btnAlumVolver);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 210, 506, 12);
		contentPane.add(separator);
		
		JLabel lblAlumno = new JLabel("ALUMNO/A:");
		lblAlumno.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAlumno.setBounds(20, 26, 77, 16);
		contentPane.add(lblAlumno);
		
		textAlumno = new JTextField();
		textAlumno.setEditable(false);
		textAlumno.setText(alumnoActual);
		textAlumno.setFont(new Font("Arial", Font.PLAIN, 12));
		textAlumno.setBounds(110, 21, 155, 26);
		contentPane.add(textAlumno);
		textAlumno.setColumns(10);
		
		JLabel lblModulo = new JLabel("MÓDULO:");
		lblModulo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblModulo.setBounds(277, 26, 92, 16);
		contentPane.add(lblModulo);
		
		cbModulo = new JComboBox<String>();
		cbModulo.addActionListener(this);
		cbModulo.setFont(new Font("Arial", Font.PLAIN, 12));
		cbModulo.setBounds(353, 22, 155, 27);
		contentPane.add(cbModulo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(67, 59, 398, 144);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00F3dulo", "Profesor/a", "Nota"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(151);
		table.getColumnModel().getColumn(1).setPreferredWidth(162);
		table.getColumnModel().getColumn(2).setPreferredWidth(86);
		scrollPane.setViewportView(table);

		// Cargamos desde MySQL solo los módulos en los que está matriculado el alumno
		cargarModulosAlumno();
	} 

	private void cargarModulosAlumno() {
		DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
		modeloCombo.addElement("Selecciona una opción");
		
		if (alumnoActual != null && !alumnoActual.isEmpty()) {
			ArrayList<String> modulos = gbd.obtenerModulosAlumno(alumnoActual);
			for (String mod : modulos) {
				modeloCombo.addElement(mod);
			}
			modeloCombo.addElement("Todas");
		}
		cbModulo.setModel(modeloCombo);
	}

	private void actualizarTablaNotas() {
		if (cbModulo.getSelectedIndex() <= 0) {
			table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"Módulo", "Profesor/a", "Nota"}));
			return;
		}
		
		String seleccion = cbModulo.getSelectedItem().toString();
		String filtro = seleccion.equals("Todas") ? "Todos los módulos" : seleccion;
		
		table.setModel(gbd.obtenerNotasAlumno(alumnoActual, filtro));
		table.getColumnModel().getColumn(0).setPreferredWidth(151);
		table.getColumnModel().getColumn(1).setPreferredWidth(162);
		table.getColumnModel().getColumn(2).setPreferredWidth(86);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		if (ob.equals(btnAlumSalir)) {
			System.exit(0);
		} else if (ob.equals(btnAlumVolver)) {
			VentanaLogeo vl = new VentanaLogeo();
			vl.setVisible(true);
			dispose();
		} else if (ob.equals(cbModulo)) {
			actualizarTablaNotas();
		}
	}
}
