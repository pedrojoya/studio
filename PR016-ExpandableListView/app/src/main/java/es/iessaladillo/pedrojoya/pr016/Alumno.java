package es.iessaladillo.pedrojoya.pr016;

// Clase para modelar el Alumno.
class Alumno {

	// Variables miembro.
	private String nombre;
	private int edad;
	private String ciclo;
	private String curso;

	// Constructores.
	public Alumno(String nombre, int edad, String ciclo, String curso) {
		this.nombre = nombre;
		this.edad = edad;
		this.ciclo = ciclo;
		this.curso = curso;
	}

	public Alumno() {
	}

	// Getters and Setters.
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}
