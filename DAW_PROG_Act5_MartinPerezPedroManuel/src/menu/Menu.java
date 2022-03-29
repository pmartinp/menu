package menu;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Menu {
	
	static int[] diasMes= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	static LocalDate todaysDate = LocalDate.now();
	
	public static void main(String[] args) {
		/* El menú que se presentará al usuario será el siguiente:
1. Crear usuario
2. Añadir datos personales
3. Iniciar sesión
4. Cambiar contraseña
5. Salir del programa*/

		Scanner leer= new Scanner(System.in);
		int selectormenu=0, dia=0, mes=0, anio=0, selectoropcion=0;
		String usuario="", contrasena="", nombre="", apellidos="", email="", iniciousuario, iniciocontrasena;
		boolean creaciondeusuario=false, validacionfecha=false, sesion=false, menu=true, cambiocontra=false;
        
		while(menu==true) {
			
			menu();
			
			selectormenu=leer.nextInt();
			if(selectormenu<1 || selectormenu>5) {
				System.out.println("Esa opción no está disponible en el menú, porfavor elija una opción disponible");
				selectormenu=leer.nextInt();
			}
			
			switch(selectormenu) {
			//crear usuario
			case 1:
				
				//Pedimos el nombre de usuario y comprobamos que siga los parámetros indicados				
				System.out.println("El nombre de usuario no debe contener espacios, ni caracteres especiales, solo números y\r\n"
						+ "letras minúsculas. Y debe tener una longitud mínima de 6 caracteres y máxima de 10.\r\n");
				
				usuario=crearUsuario();
					
				//Pedimos la contraseña y comprobamos que siga los parámetros indicados
				System.out.println("\r\nLa contraseña puede tener todo tipo de caracteres menos / \\ y espacios. Y debe tener una\r\n"
						+ "longitud mínima de 8 caracteres y máxima de 12.\r\n");
				contrasena=crearContra();
				
				creaciondeusuario=true;
				
				break;
				
			//Añadir datos personales
			case 2:
				
				//comprobamos que antes de introducir los datos personales el usuario ha sido creado
				if(creaciondeusuario==true) {
					System.out.println("El nombre no debe tener caracteres especiales ni números.");
					
					nombre=crearNombre_Apellidos();
					
					System.out.println("\r\nLos apellidos no debe tener caracteres especiales ni números.");
					
					apellidos=crearNombre_Apellidos();
					
					email=crearEmail();
					
					//Se pide el día mes y año por separado y se utiliza una función que devuelve un valor booleano para comprobar que sea correcto
					while(validacionfecha==false) {
						
						System.out.print("\r\nDía: ");
						dia=leer.nextInt();
						System.out.print("\r\nMes: ");
						mes=leer.nextInt();
						System.out.print("\r\nAño: ");
						anio=leer.nextInt();
						
						validacionfecha=validarFechaNac(dia, mes, anio, diasMes);
					}
					validacionfecha=false;
				}else {
					System.out.println("Debes crear un usuario antes de introducir sus datos personales \r\n");
				}
				
				break;
				
			//Iniciar Sesion
			case 3:
				
				if(creaciondeusuario==true) {
					
					sesion=inicioSesion(usuario, contrasena);
					
					if(sesion==true) {
						System.out.println("\r\nBienvenido al sistema "+ nombre + ", hoy es "+ todaysDate +"\r\n");
					}else {
						while(sesion==false) {
							System.out.println("1. Volver a Intentar");
							System.out.println("2. Atrás");
							selectoropcion=leer.nextInt();
							
							if(selectoropcion==1) {
								sesion=inicioSesion(usuario, contrasena);
							}else if(selectoropcion==2) {
								break;
							}
						}
						selectoropcion=0;
						if(sesion==true) {
							System.out.println("\r\nBienvenido al sistema "+ nombre + ", hoy es "+ todaysDate +"\r\n");
						}
					}
					
				}else {
					System.out.println("Debes crear un usuario antes de iniciar sesion \r\n");
				}
				
				break;
				
			//Cambiar contraseña
			case 4:
				
				if(creaciondeusuario==true) {
					
					cambiocontra=cambiarContrasena(contrasena);
					
					if(cambiocontra==true) {
						System.out.print("Nueva ");
						contrasena=crearContra();
					}else {
						while(cambiocontra==false) {
							System.out.println("1. Volver a Intentar");
							System.out.println("2. Atrás");
							selectoropcion=leer.nextInt();
							
							if(selectoropcion==1) {
								cambiocontra=cambiarContrasena(contrasena);
							}else if(selectoropcion==2) {
								break;
							}
						}
						selectoropcion=0;
						if(sesion==true) {
							System.out.print("Nueva ");
							contrasena=crearContra();
						}
					}
					
				}else {
					System.out.println("Debes crear un usuario antes de cambiar la contraseña \r\n");
				}

				break;
			
			//Salir del programa
			case 5:
				
				int edad=calcularEdad(dia, mes, anio);
				mensajeSalida(creaciondeusuario, nombre, apellidos, usuario, contrasena, email, dia, mes, anio, edad);
				
				menu=false;
				break;
				
			}
			}
		
		
		}
	
	static void menu () {		
		System.out.println("Menú");
		System.out.println("1. Crear usuario");
		System.out.println("2. Añadir datos personales");
		System.out.println("3. Iniciar sesión");
		System.out.println("4. Cambiar contraseña");
		System.out.println("5. Salir del programa");

	}
	
	public static String crearUsuario() {
		//Pedimos el usuario
		Scanner leer= new Scanner(System.in);
		String usuario;
		System.out.print("Usuario: ");
		usuario=leer.nextLine();
		
		if(usuario.matches("[a-z0-9]{6,10}")) {
			return usuario;
		}
		else {
			System.out.println("El nombre de usuario introducido no cumple con los requisitos");
			return crearUsuario();
		}
	}
	
	public static String crearContra() {
		//Pedimos la contraseña
		Scanner leer= new Scanner(System.in);
		String contra;
		System.out.print("Contraseña: ");
		contra=leer.nextLine();
		
		if(contra.matches(".*/.*") || contra.matches(".*\\\\.*") || contra.length()<8 || contra.length()>12) {
				System.out.println("La contraseña introducida no cumple con los requisitos");
				return crearContra();
		}
		else {
			return contra;
		}
	}
	
	public static String crearNombre_Apellidos() {
		//Pedimos la contraseña
		Scanner leer= new Scanner(System.in);
		String texto;
		System.out.print("Texto: ");
		texto=leer.nextLine();
		
		if(texto.matches("[a-zA-Z\\s]*")) {
			return texto;
		}
		else {
			System.out.println("Los datos introducidos son incorrectos");
			return crearNombre_Apellidos();
		}
	}
	
	public static String crearEmail() {
		Scanner leer= new Scanner(System.in);
		String email;
		System.out.print("\r\nEmail: ");
		email=leer.nextLine();
		
		Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
        Matcher mather = pattern.matcher(email);
 
        if (mather.find() == true) {
            return email;
        } else {
            System.out.println("El email ingresado no es inválido.");
            return crearEmail();
        }
	}
	
	public static boolean validarFechaNac(int dia, int mes, int anio, int[]diasMes) {
		LocalDate fechaActual = LocalDate.now();
		//comprobamos mes
		if(mes<1 || mes>12) {
			System.out.println("La fecha introducida no es válida");
			return false;
		}
		//comprobamos año
		else if (anio<1900 || anio>fechaActual.getYear()){
			System.out.println("La fecha introducida no es válida");
			return false;
		}
		//comprobamos años bisiestos
		else if(dia>1 && dia<=29 && mes==2 && (anio%4)==0){
			return true;
		}
		//comprobamos días
		else if(dia<1 || dia>diasMes[mes-1]) {
			System.out.println("La fecha introducida no es válida");
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean inicioSesion(String usuario, String contrasena) {
		Scanner leer= new Scanner(System.in);
		String iniciousuario, iniciocontra;
		System.out.print("Usuario: ");
		iniciousuario=leer.nextLine();
		System.out.print("Contraseña: ");
		iniciocontra=leer.nextLine();
		if(iniciousuario.equals(usuario) && iniciocontra.equals(contrasena)) {
			return true;
		}else {
			System.out.println("El usuario o la contraseña introducidos son incorrectos");
			return false;
		}
	}
	
	public static boolean cambiarContrasena(String contrasena) {
		Scanner leer= new Scanner(System.in);
		String  contra;
		System.out.print("Introduzca su antigua contraseña: ");
		contra=leer.nextLine();
		if(contra.equals(contrasena)) {
			return true;
		}else {
			System.out.println("Contraseña incorrecta");
			return false;
		}
	}
	
	public static int calcularEdad(int dia, int mes, int anio) {
		
		int edad=0;
		if(todaysDate.getMonthValue()<mes) {
			anio++;
		}
		else if(todaysDate.getMonthValue()==mes && todaysDate.getDayOfMonth()<dia) {
			anio++;
		}
		edad=todaysDate.getYear()-anio;
		return edad;
	}
	
	public static void mensajeSalida(boolean creaciondeusuario, String nombre, String apellidos, String usuario, String contrasena, String email, int dia, int mes, int anio, int edad) {
		
		if(creaciondeusuario==false) {
			System.out.println("- FIN DEL PROGRAMA -");
		}else {
			System.out.println("Nombre: "+nombre);
			System.out.println("Apellidos: "+apellidos);
			System.out.println("Nombre de usuario: "+usuario);
			System.out.println("Contraseña: "+contrasena);
			System.out.println("Email: "+email);
			System.out.println("Fecha de nacimiento: "+ dia+"/"+mes+"/"+anio);
			System.out.println("Edad: "+ edad);
		}
	}
		
	

}
