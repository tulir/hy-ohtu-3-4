package ohtu;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;

public class Main {
	public static void main(String[] args) throws IOException {
		String studentNr = "012345678";
		if (args.length > 0) {
			studentNr = args[0];
		}

		Gson mapper = new Gson();
		List<Submission> subs = mapper.fromJson(Request
				.Get("https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions")
				.execute().returnContent().asString(), new TypeToken<List<Submission>>() {}.getType());
		Course[] courses = mapper.fromJson(Request
				.Get("https://studies.cs.helsinki.fi/courses/courseinfo")
				.execute().returnContent().asString(), Course[].class);

		System.out.printf("opiskelijanumero %s\n\n", studentNr);
		for (Course c : courses) {
			List<Submission> courseSubs = subs.stream().filter(sub -> sub.course.equals(c.name)).collect(Collectors.toList());
			if (courseSubs.size() == 0) {
				continue;
			}
			System.out.printf("%s %s %d\n\n", c.fullName, c.term, c.year);

			int maxExercises = c.exercises.stream().mapToInt(i -> i).sum();
			int totalHours = 0, totalExercises = 0;
			for (Submission sub : courseSubs) {
				System.out.printf("viikko %d:\n", sub.week);
				System.out.printf(" tehtyjä tehtäviä %d/%d; aikaa kului %d tuntia; tehdyt tehtävät: %s\n",
						c.exercises.get(sub.week), sub.exercises.size(), sub.hours,
						sub.exercises.stream().map(i -> Integer.toString(i)).collect(Collectors.joining(", ")));
				totalHours += sub.hours;
				totalExercises += sub.exercises.size();
			}
			System.out.printf("\nyhteensä: %d/%d tehtävää, %d tuntia\n\n", totalExercises, maxExercises, totalHours);
			Map<String, CourseWeekStats> stats = mapper.fromJson(Request
					.Get(String.format("https://studies.cs.helsinki.fi/courses/%s/stats", c.name))
					.execute().returnContent().asString(), new TypeToken<Map<String, CourseWeekStats>>(){}.getType());
			int courseTotalSubs = 0, courseTotalExercises = 0;
			double courseTotalHours = 0;
			for (CourseWeekStats cws : stats.values()) {
				courseTotalSubs += cws.students;
				courseTotalExercises += cws.exercise_total;
				courseTotalHours += cws.hour_total;
			}
			System.out.printf("kurssilla yhteensä %d palautusta, palautettuja tehtäviä %d kpl, aikaa käytetty yhteensä %d tuntia\n\n",
					courseTotalSubs, courseTotalExercises, (int) courseTotalHours);
		}
	}
}