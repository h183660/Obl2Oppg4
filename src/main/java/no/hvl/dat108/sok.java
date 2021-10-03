package no.hvl.dat108;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Year;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sok")
public class sok extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String aarString = request.getParameter("aar");

		PrintWriter out = response.getWriter();

		if (erGyldigAarstall(aarString)) {
			int aar = Integer.parseInt(aarString);

			// Dette funker bare hvis det er en konge fra Konger.norske,
			// vi la ikke til flere så kan få feil med f.eks år 1900
			Konge konge = Konger.norske.stream()
					.filter(x -> x.getKongeFraAar() <= aar && x.getKongeTilAar() >= aar)
					.findFirst()
					.get();

			response.setContentType("text/html");

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Konge</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<img src=\" " + konge.getBilde() + "\">");
			out.println("<p>Konge i år " + aar + " var " + konge.getNavn() + ", født " + konge.getFodtAar()
					+ ", konge fra " + konge.getKongeFraAar() + " til " + konge.getKongeTilAar() + "</p>");
			out.println("<a href=\"index.html\">Nytt søk</a>");
			out.println("</body>");
			out.println("<html>");
		} else {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Feil under søk</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p> Feil under søk, du må skrive et årstall der det fantes en konge i Norge,"
					+ " <a href=\"index.html\">prøv igjen</a></p>");
			out.println("</body>");
			out.println("<html>");
		}
	}

	private boolean erGyldigAarstall(String aarsTall) {
		if (aarsTall.matches("^\\d+$")) {
			return Integer.parseInt(aarsTall) >= 885 && Integer.parseInt(aarsTall) <= Year.now().getValue();
		}
		return false;
	}
}