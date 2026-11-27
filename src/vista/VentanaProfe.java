package vista;

import baseDatos.GestionBD;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class VentanaProfe extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textNombreProfe;
	private JComboBox<String> cbModulo;
	private JButton btnProfeSalir;
	private JButton btnProfVolver;
	private JButton btnEliminar;
	private JButton btnInsertar;

	private GestionBD gbd = new GestionBD();
	private String profeActual;

	// Constructor vacío para que WindowBuilder pueda abrir la vista Diseño
	public VentanaProfe() {
		this("");
	}

	// Constructor principal que recibe el usuario del profesor desde VentanaLogeo
	public VentanaProfe(String profesorUsuario) {
		this.profeActual = profesorUsuario;

		setResizable(false);
		setTitle("VENTANA PROFESOR/A");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(288, 17, 394, 183);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"ID Matrícula", "Alumnos", "Notas"}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		
		btnProfeSalir = new JButton("SALIR");
		btnProfeSalir.setFont(new Font("Arial", Font.PLAIN, 11));
		btnProfeSalir.addActionListener(this);
		btnProfeSalir.setBounds(597, 268, 98, 44);
		contentPane.add(btnProfeSalir);
		
		btnProfVolver = new JButton("VOLVER");
		btnProfVolver.setFont(new Font("Arial", Font.PLAIN, 11));
		btnProfVolver.addActionListener(this);
		btnProfVolver.setBounds(507, 268, 93, 44);
		contentPane.add(btnProfVolver);
		
		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setFont(new Font("Arial", Font.PLAIN, 11));
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(597, 212, 85, 29);
		contentPane.add(btnEliminar);
		
		btnInsertar = new JButton("INSERTAR");
		btnInsertar.setFont(new Font("Arial", Font.PLAIN, 11));
		btnInsertar.addActionListener(this);
		btnInsertar.setBounds(507, 212, 93, 29);
		contentPane.add(btnInsertar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 253, 689, 14);
		contentPane.add(separator);
		
		JLabel lProfesor = new JLabel("PROFESOR/A:");
		lProfesor.setFont(new Font("Arial", Font.PLAIN, 12));
		lProfesor.setBounds(20, 36, 84, 16);
		contentPane.add(lProfesor);
		
		textNombreProfe = new JTextField();
		textNombreProfe.setEditable(false);
		textNombreProfe.setText(profeActual);
		textNombreProfe.setFont(new Font("Arial", Font.PLAIN, 12));
		textNombreProfe.setBounds(116, 30, 146, 26);
		contentPane.add(textNombreProfe);
		textNombreProfe.setColumns(10);
		
		JLabel lblModulo = new JLabel("MÓDULO:");
		lblModulo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblModulo.setBounds(20, 72, 61, 16);
		contentPane.add(lblModulo);
		
		cbModulo = new JComboBox<String>();
		cbModulo.addActionListener(this);
		cbModulo.setFont(new Font("Arial", Font.PLAIN, 11));
		cbModulo.setBounds(116, 68, 146, 27);
		contentPane.add(cbModulo);

		// Cargamos desde MySQL únicamente los módulos que imparte este profesor
		cargarModulosProfesor();
	}

	private void cargarModulosProfesor() {
		DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
		modeloCombo.addElement("Seleccione un módulo");
		
		if (profeActual != null && !profeActual.isEmpty()) {
			ArrayList<String> modulos = gbd.obtenerModulosProfesor(profeActual);
			for (String mod : modulos) {
				modeloCombo.addElement(mod);
			}
		}
		cbModulo.setModel(modeloCombo);
	}

	private void cargarAlumnosDelModulo() {
		if (cbModulo.getSelectedIndex() <= 0) {
			table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"ID Matrícula", "Alumnos", "Notas"}));
			return;
		}
		String moduloSeleccionado = cbModulo.getSelectedItem().toString();
		table.setModel(gbd.obtenerAlumnosPorModulo(moduloSeleccionado));
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		if (ob.equals(btnProfeSalir)) {
			System.exit(0);
		} else if (ob.equals(btnProfVolver)) {
			VentanaLogeo vl = new VentanaLogeo();
			vl.setVisible(true);
			dispose();
		} else if (ob.equals(cbModulo)) {
			cargarAlumnosDelModulo();
		} else if (ob.equals(btnInsertar)) {
			int fila = table.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(null, "Selecciona un alumno de la tabla primero");
				return;
			}
			int idMatricula = (int) table.getValueAt(fila, 0);
			String nombreAlum = table.getValueAt(fila, 1).toString();
			String input = JOptionPane.showInputDialog(null, "Introduce la nota para " + nombreAlum + " (0 a 10):");
			
			if (input != null && !input.trim().isEmpty()) {
				try {
					double nota = Double.parseDouble(input.replace(",", "."));
					if (nota < 0 || nota > 10) {
						JOptionPane.showMessageDialog(null, "La nota debe estar entre 0 y 10");
					} else if (gbd.actualizarNota(idMatricula, nota)) {
						JOptionPane.showMessageDialog(null, "Nota guardada correctamente");
						cargarAlumnosDelModulo(); // Refresca la tabla
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Introduce un número válido");
				}
			}
		} else if (ob.equals(btnEliminar)) {
			int fila = table.getSelectedRow();
			if (fila == -1) {
				JOptionPane.showMessageDialog(null, "Selecciona un alumno de la tabla primero");
				return;
			}
			int idMatricula = (int) table.getValueAt(fila, 0);
			if (gbd.eliminarNota(idMatricula)) {
				JOptionPane.showMessageDialog(null, "Nota eliminada");
				cargarAlumnosDelModulo();
			}
		}
	}
}

