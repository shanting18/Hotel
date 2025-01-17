package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.RegistroControlador;
import controlador.ReservasControlador;
import modelo.Huespedes;
import modelo.Reservas;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.util.List;
import java.util.Optional;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	 RegistroControlador registroControlador;
	 ReservasControlador reservasControlador;
	 ReservasView reservasView;
	 String reserva;
	 String huesped;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		
		try {
			reservasControlador = new ReservasControlador();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			registroControlador = new RegistroControlador();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("NUMERO DE RESERVA");
		modelo.addColumn("FECHA DE INICIO");
		modelo.addColumn("FECHA DE SALIDA");
		modelo.addColumn("VALOR");
		modelo.addColumn("FORMA DE PAGO");
		try {
			mostrartabla();
		} catch (SQLException e) {
			System.out.println("error 1");
			e.printStackTrace();
		}
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		try {
			mostrartablaHuesped();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "error 2" );
			e.printStackTrace();
		}
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eliminarTabla();
				if (txtBuscar.getText().equals("")) {
					try {
						mostrartabla();
						
					} catch (SQLException e1) {
						throw new RuntimeException(e1);
					}
					try {
						mostrartablaHuesped();
					} catch (Exception e2) {
						
					}
					
				} else {
					try {
						mostrartablaId();
						
					} catch (SQLException e1) {
						throw new RuntimeException(e1);
					}
					try {
						mostrartablaHuespedID();
					} catch (Exception e2) {
						throw new RuntimeException(e2);
					}
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filareserva = tbReservas.getSelectedRow();
				int filahuesped = tbHuespedes.getSelectedRow();
				if(filareserva > 0) {
					eliminarTabla();
					actualizarTabla();
					try {
						mostrartabla();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else if(filahuesped > 0) {
					eliminarTabla();
					actualizarTablaHuesped();
					try {
						mostrartablaHuesped();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filareserva = tbReservas.getSelectedRow();
				int filahuesped = tbHuespedes.getSelectedRow();
				if(filareserva >= 0) {
					reserva = tbReservas.getValueAt(filareserva, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "deseas eliminar");
					if (confirmar == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(filareserva, 0).toString();
						reservasControlador.eliminar(Integer.valueOf(valor));
						JOptionPane.showInternalMessageDialog(contentPane, "eliminado");
						eliminarTabla();
						try {
							mostrartabla();
							mostrartablaHuesped();
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
					}
				}else if (filahuesped >= 0) {
					huesped = tbHuespedes.getValueAt(filahuesped, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "deseas eliminar");
					if (confirmar == JOptionPane.YES_OPTION) {
						String valorH = tbHuespedes.getValueAt(filahuesped, 0).toString();
						registroControlador.eliminar(Integer.valueOf(valorH));
						JOptionPane.showInternalMessageDialog(contentPane, "eliminado");
						eliminarTabla();
						try {
							mostrartabla();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						try {
							mostrartablaHuesped();
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	private List<Reservas> motrarReservas() throws SQLException{
		return this.reservasControlador.listar();
	}
	
	private List<Reservas> buscarReservas() throws SQLException{
		return this.reservasControlador.buscarReservas(txtBuscar.getText());
	}
	
	private void mostrartabla() throws SQLException {
		List<Reservas> reserva = motrarReservas();
		try {
			for(Reservas reservas : reserva) {
				modelo.addRow(new Object[] {
						reservas.getId(), reservas.getFecha_entrada(),reservas.getFecha_salida(),reservas.getValor(),reservas.getForma_de_pago()
				});
			}
		} catch (Exception e) {
			 
		}
	}
	
	private void mostrartablaId() throws SQLException {
		List<Reservas> reserva = buscarReservas();
		try {
			for(Reservas reservas : reserva) {
				modelo.addRow(new Object[] {
						reservas.getId(), reservas.getFecha_entrada(),reservas.getFecha_salida(),reservas.getValor(),reservas.getForma_de_pago()
				});
			}
		} catch (Exception e) {
			 
		}
	}
	
	private void actualizarTabla() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(),tbReservas.getSelectedColumn())).ifPresent(
				fila -> {
					
					Date fecha_entrada = null;
					Date fecha_salida = null;
					
					try {
						
						 fecha_entrada = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString());
						 fecha_salida = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString());
						 
						calcularPrecio(fecha_entrada, fecha_salida);
						String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
						Reservas reserva = new Reservas(fecha_entrada, fecha_salida, calcularPrecio(fecha_entrada, fecha_salida), formaPago);
						reservasControlador.actualizar(reserva);
						JOptionPane.showInternalMessageDialog(this, "Reserva modificada con éxito!");
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
				});
	}
	
	public String calcularPrecio(Date fecha_entrada, Date fecha_salida) {
		if(fecha_entrada != null && fecha_salida != null) {
			
			int dias = 0;
			int valor = dias * 50000;
			return Integer.toString(valor);
			
		}else {
			return "";
		}
	}
	
	
	private List<Huespedes> motrarHuespedes() throws SQLException{
		return this.registroControlador.listar();
	}
	
	private List<Huespedes> buscarHuespedes() throws SQLException{
		return this.registroControlador.buscarHuespedes(txtBuscar.getText());
	}
	
	private void mostrartablaHuesped() throws SQLException {
		List<Huespedes> huespedes = motrarHuespedes();
		try {
			for(Huespedes huesped : huespedes) {
				modeloHuesped.addRow(new Object[] {
						huesped.getId(),huesped.getNombre(),huesped.getApellido(),huesped.getFecha_de_nacimiento(),
						huesped.getNacionalidad(),huesped.getTelefono(),huesped.getId_reseva()
				});
			}
		} catch (Exception e) {
			 
		}
	}
	
	private void mostrartablaHuespedID() throws SQLException {
		List<Huespedes> huespedes = buscarHuespedes();
		try {
			for(Huespedes huesped : huespedes) {
				modeloHuesped.addRow(new Object[] {
						huesped.getId(),huesped.getNombre(),huesped.getApellido(),huesped.getFecha_de_nacimiento(),
						huesped.getNacionalidad(),huesped.getTelefono(),huesped.getId_reseva()
				});
			}
		} catch (Exception e) {
			 
		}
	}
	
	private void actualizarTablaHuesped() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),tbHuespedes.getSelectedColumn())).ifPresent(
				filaHuesped -> {
					
					try {	
						Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1).toString());
						String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
						String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3);
						Date fechaNacimiento = Date.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4).toString());
						String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
						String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6);
						Integer idReserva = (Integer) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 7);
						
						
						Huespedes huesped = new Huespedes(id,nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
						
						 registroControlador.actualizar(huesped);
						
						JOptionPane.showMessageDialog(new Frame(), "cambio realizado");
					} catch (Exception e) {
						
					}
					
					
				});
	}
	
	private void eliminarTabla() {
		((DefaultTableModel)tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel)tbReservas.getModel()).setRowCount(0);
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
