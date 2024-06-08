package indexWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
	private final String GET_MOVIE_SCRIPTS_SQL = "SELECT * FROM script;";

	public List<MovieScript> getMovieScripts() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		List<MovieScript> movieScripts = new ArrayList<>();

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:scripts.sqlite");
			statement = connection.createStatement();

			resultSet = statement.executeQuery(GET_MOVIE_SCRIPTS_SQL);

			while (resultSet.next()) {
				MovieScript movieScript = new MovieScript(resultSet.getLong(1),
					resultSet.getLong(2),
					resultSet.getString(3),
					resultSet.getString(4),
					resultSet.getInt(5));

				movieScripts.add(movieScript);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 해제
				if (resultSet != null) resultSet.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return movieScripts;
	}
}
