package vista;
import baseDatos.GestionBD;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VentanaLogeo extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField pfpassword;
	private JButton btnSalir;
	private JButton btnEntrar;
	private JButton btnLimpiar;
	private JComboBox<String> cbCargo;
	
	
	private GestionBD gbd = new GestionBD();
	private VentanaProfe vp;
	private VentanAlum va;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogeo frame = new VentanaLogeo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public VentanaLogeo() {
		setResizable(false);
		setTitle("Ventana de Inicio");
		setFont(new Font("Arial", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaLogeo.class.getResource("/imagenes/favicon-16x16.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IUsuario = new JLabel("USUARIO");
		IUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		IUsuario.setBounds(17, 64, 61, 16);
		contentPane.add(IUsuario);
		
		textUsuario = new JTextField();
		textUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		textUsuario.setBounds(113, 59, 130, 26);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lContraseña = new JLabel("CONTRASEÑA");
		lContraseña.setFont(new Font("Arial", Font.PLAIN, 12));
		lContraseña.setBounds(17, 104, 98, 16);
		contentPane.add(lContraseña);
		
		pfpassword = new JPasswordField();
		pfpassword.setEchoChar('*');
		pfpassword.setFont(new Font("Arial", Font.PLAIN, 12));
		pfpassword.setBounds(113, 99, 130, 26);
		contentPane.add(pfpassword);
		
		JLabel lCargo = new JLabel("CARGO");
		lCargo.setFont(new Font("Arial", Font.PLAIN, 12));
		lCargo.setBounds(17, 143, 61, 16);
		contentPane.add(lCargo);
		
		cbCargo = new JComboBox<String>();
		cbCargo.addActionListener(this);
		cbCargo.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecciona una opción", "Profesor/a", "Alumno/a"}));
		cbCargo.setFont(new Font("Arial", Font.PLAIN, 9));
		cbCargo.setBounds(113, 132, 146, 27);
		contentPane.add(cbCargo);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaLogeo.class.getResource("/imagenes/Universidad2.png")));
		lblNewLabel.setBounds(255, 24, 185, 80);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(17, 171, 423, 12);
		contentPane.add(separator);
		
		btnLimpiar = new JButton("LIMPIAR");
		btnLimpiar.setFont(new Font("Arial", Font.PLAIN, 11));
		btnLimpiar.addActionListener(this);
		btnLimpiar.setBounds(17, 195, 98, 29);
		contentPane.add(btnLimpiar);
		
		btnEntrar = new JButton("ENTRAR");
		btnEntrar.addActionListener(this);
		btnEntrar.setFont(new Font("Arial", Font.PLAIN, 11));
		btnEntrar.setBounds(218, 195, 88, 29);
		contentPane.add(btnEntrar);
		
		btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(this);
		btnSalir.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSalir.setBounds(323, 195, 117, 29);
		contentPane.add(btnSalir);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object ob = e.getSource();
		
		if (ob.equals(btnEntrar)) {
			
			String usuario = textUsuario.getText();
			String password = new String(pfpassword.getPassword());
			
			if(usuario.equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(null, "Algún valor vacío");
			} else if(cbCargo.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Debes seleccionar un cargo");
			
			}else {
				try {
					String cargo = cbCargo.getSelectedItem().toString();
					if (gbd.buscarUsuarios(usuario, password, cargo)) {
						JOptionPane.showMessageDialog(null, "Bienvenido " + usuario);
						
						if(cbCargo.getSelectedItem().equals("Profesor/a")){
						vp = new VentanaProfe(usuario);
						vp.setVisible(true);
						dispose(); //Cerramos la ventana actual
					} else if(cbCargo.getSelectedItem().equals("Alumno/a")) {
						va = new VentanAlum(usuario);
						va.setVisible(true);
						dispose();//cerramos la ventana actual			
					}
				} else {
					JOptionPane.showMessageDialog(null, "Datos incorrectos");
				}
					
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error al conectar :" + ex.getMessage());
				ex.printStackTrace();
				
			}
		}
		
		
		} else if (ob.equals(btnSalir)) {
			System.exit(EXIT_ON_CLOSE);
		}else if (ob.equals(btnLimpiar)) {
			textUsuario.setText("");;
			pfpassword.setText("");;
			cbCargo.setSelectedItem(0);
		}
				
		
	}

}

