import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

/**
[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
 */

data class Shift(val guard: Int, val from: LocalDateTime, var to: LocalDateTime, val naps: MutableList<Nap>)

data class Nap(val from: LocalDateTime, var to: LocalDateTime)


fun MatchResult.getShift(date: LocalDateTime) = let {

    val guard = it
        .destructured
        .component1()
        .toInt()
    Shift(guard, date, date, mutableListOf())
}

fun List<String>.parseShifts() =
    this.map {
        """\[(\d{4}\-\d{2}\-\d{2})\ (\d{2}\:\d{2})\]\ (.*)""".toRegex().find(it)
    }.fold(listOf<Shift>()) { init, row ->
        val (date, time, text) = row!!.destructured
        val d = LocalDateTime.parse("${date}T${time}")
        when {
            text.startsWith("Guard") ->
                """.*#(\d{1,4}).*""".toRegex()
                    .find(text)!!
                    .getShift(d)
                    .let { init + it }
            text.startsWith("falls") -> init.apply {
                last().let {
                    it.naps += Nap(d, d)
                    it.to = d
                }
            }
            text.startsWith("wakes") -> init.apply {
                last().let {
                    it.naps.last().to = d
                    it.to = d
                }
            }
            else -> init
        }
    }


fun solveDay4(input: String) =
    input
        .split("\n")
        .sorted()
        .parseShifts()
        .groupingBy { it.guard }
        .fold({_,_ ->0},
            { key, acc, shift ->
            acc + shift.naps.sumBy { ChronoUnit.MINUTES.between(shift.from,shift.to).toInt()
            }
        })
        .maxBy { it.value }
        .let{ it!!.key * it.value}



fun main() {
    val solution = solveDay4(data4)
    println(solution)


}

val data4 = """[1518-03-30 00:57] wakes up
[1518-04-15 23:56] Guard #2213 begins shift
[1518-10-31 00:36] wakes up
[1518-11-14 00:03] Guard #2129 begins shift
[1518-04-01 00:54] wakes up
[1518-10-03 00:42] falls asleep
[1518-07-01 00:19] falls asleep
[1518-08-02 00:00] Guard #3319 begins shift
[1518-07-03 00:01] falls asleep
[1518-08-28 00:24] falls asleep
[1518-11-02 00:31] falls asleep
[1518-10-15 00:04] falls asleep
[1518-08-07 00:51] wakes up
[1518-05-02 00:14] falls asleep
[1518-05-16 00:38] falls asleep
[1518-08-27 00:37] falls asleep
[1518-09-18 00:47] wakes up
[1518-05-29 00:52] wakes up
[1518-09-07 00:06] falls asleep
[1518-07-14 00:52] wakes up
[1518-05-09 00:59] wakes up
[1518-05-14 00:12] falls asleep
[1518-04-17 23:51] Guard #439 begins shift
[1518-11-20 00:04] Guard #2129 begins shift
[1518-07-21 00:02] Guard #3347 begins shift
[1518-11-16 00:04] Guard #241 begins shift
[1518-04-02 23:48] Guard #1777 begins shift
[1518-07-11 00:00] Guard #241 begins shift
[1518-07-29 00:49] falls asleep
[1518-09-14 00:38] falls asleep
[1518-05-27 00:39] wakes up
[1518-04-09 00:54] wakes up
[1518-11-01 23:56] Guard #103 begins shift
[1518-10-07 00:42] wakes up
[1518-09-26 00:58] wakes up
[1518-09-10 00:54] falls asleep
[1518-08-15 00:48] falls asleep
[1518-06-09 00:01] Guard #2251 begins shift
[1518-11-06 23:58] Guard #103 begins shift
[1518-10-04 00:29] falls asleep
[1518-08-02 00:56] falls asleep
[1518-08-21 00:18] falls asleep
[1518-11-23 00:30] falls asleep
[1518-09-29 23:59] Guard #829 begins shift
[1518-09-17 00:49] falls asleep
[1518-07-25 00:49] wakes up
[1518-08-19 00:58] wakes up
[1518-07-13 00:48] wakes up
[1518-04-11 23:59] Guard #2539 begins shift
[1518-04-30 00:32] wakes up
[1518-07-05 00:27] falls asleep
[1518-09-02 00:29] wakes up
[1518-09-21 00:58] wakes up
[1518-04-26 00:08] falls asleep
[1518-04-23 00:26] wakes up
[1518-05-25 00:55] wakes up
[1518-05-14 23:58] Guard #241 begins shift
[1518-09-09 23:58] Guard #3319 begins shift
[1518-04-23 00:23] falls asleep
[1518-08-08 00:42] wakes up
[1518-06-12 00:03] falls asleep
[1518-03-31 00:03] Guard #103 begins shift
[1518-04-11 00:58] wakes up
[1518-03-28 00:53] falls asleep
[1518-06-29 23:57] Guard #103 begins shift
[1518-05-08 00:36] falls asleep
[1518-11-18 00:00] Guard #2137 begins shift
[1518-03-31 00:21] falls asleep
[1518-05-08 00:57] falls asleep
[1518-06-18 00:50] falls asleep
[1518-05-08 00:06] falls asleep
[1518-05-09 00:23] falls asleep
[1518-08-24 00:00] Guard #1301 begins shift
[1518-07-07 00:57] wakes up
[1518-10-24 00:51] wakes up
[1518-08-29 00:31] falls asleep
[1518-08-27 00:51] falls asleep
[1518-08-19 00:54] falls asleep
[1518-10-05 00:02] Guard #3347 begins shift
[1518-04-04 00:14] falls asleep
[1518-10-17 00:01] Guard #2389 begins shift
[1518-05-07 00:57] wakes up
[1518-11-17 00:01] Guard #1889 begins shift
[1518-07-09 00:54] wakes up
[1518-07-12 00:02] falls asleep
[1518-04-23 00:00] Guard #2137 begins shift
[1518-05-02 00:00] Guard #1283 begins shift
[1518-07-08 23:58] Guard #2389 begins shift
[1518-11-08 00:48] falls asleep
[1518-11-06 00:48] wakes up
[1518-06-25 00:46] wakes up
[1518-06-03 23:57] Guard #3371 begins shift
[1518-05-19 23:50] Guard #3371 begins shift
[1518-06-10 00:04] Guard #3371 begins shift
[1518-11-20 00:36] falls asleep
[1518-10-10 23:58] Guard #2539 begins shift
[1518-07-09 00:42] falls asleep
[1518-09-09 00:21] wakes up
[1518-05-10 00:03] Guard #3347 begins shift
[1518-06-18 00:47] wakes up
[1518-05-16 00:55] wakes up
[1518-06-23 00:41] falls asleep
[1518-08-27 00:39] wakes up
[1518-05-26 00:03] falls asleep
[1518-07-10 00:03] Guard #2389 begins shift
[1518-10-14 00:02] Guard #2389 begins shift
[1518-06-09 00:40] wakes up
[1518-07-01 00:55] wakes up
[1518-04-11 00:15] falls asleep
[1518-10-11 00:55] wakes up
[1518-06-25 00:24] falls asleep
[1518-04-29 00:45] falls asleep
[1518-11-04 00:02] Guard #1889 begins shift
[1518-07-20 00:04] Guard #2129 begins shift
[1518-05-06 00:32] falls asleep
[1518-04-27 00:08] falls asleep
[1518-11-11 00:33] falls asleep
[1518-11-21 00:22] falls asleep
[1518-10-28 00:50] falls asleep
[1518-07-24 00:19] falls asleep
[1518-10-21 00:10] falls asleep
[1518-09-19 00:01] Guard #2389 begins shift
[1518-03-31 00:57] wakes up
[1518-11-20 00:46] wakes up
[1518-05-28 00:41] falls asleep
[1518-11-18 23:56] Guard #439 begins shift
[1518-04-04 00:50] falls asleep
[1518-10-09 00:27] wakes up
[1518-11-16 00:47] falls asleep
[1518-08-03 00:42] wakes up
[1518-10-17 00:57] falls asleep
[1518-10-27 00:22] wakes up
[1518-07-29 00:03] Guard #2251 begins shift
[1518-10-06 00:18] falls asleep
[1518-07-31 00:23] falls asleep
[1518-09-05 00:00] Guard #1283 begins shift
[1518-11-10 23:59] Guard #439 begins shift
[1518-10-16 00:53] wakes up
[1518-10-31 00:57] wakes up
[1518-09-15 00:58] wakes up
[1518-05-30 00:59] wakes up
[1518-08-13 00:38] wakes up
[1518-09-08 00:42] wakes up
[1518-09-05 00:17] falls asleep
[1518-03-28 23:59] Guard #1777 begins shift
[1518-06-11 00:52] falls asleep
[1518-06-16 00:46] falls asleep
[1518-06-22 00:49] wakes up
[1518-06-27 23:58] Guard #2389 begins shift
[1518-10-15 00:42] wakes up
[1518-10-14 00:11] falls asleep
[1518-07-10 00:52] falls asleep
[1518-06-14 00:22] falls asleep
[1518-11-01 00:50] wakes up
[1518-09-21 23:59] Guard #1777 begins shift
[1518-05-23 00:43] wakes up
[1518-05-11 00:47] wakes up
[1518-09-02 23:57] Guard #1889 begins shift
[1518-06-12 00:32] wakes up
[1518-03-31 00:54] falls asleep
[1518-10-18 00:46] falls asleep
[1518-08-24 23:56] Guard #1889 begins shift
[1518-04-10 00:21] wakes up
[1518-07-23 00:01] Guard #3347 begins shift
[1518-10-08 00:08] falls asleep
[1518-09-28 00:00] falls asleep
[1518-04-18 00:57] wakes up
[1518-05-26 00:44] falls asleep
[1518-10-30 00:22] falls asleep
[1518-11-14 00:42] falls asleep
[1518-11-04 00:40] wakes up
[1518-04-09 00:38] falls asleep
[1518-06-24 23:57] Guard #1283 begins shift
[1518-06-02 00:01] Guard #1283 begins shift
[1518-07-16 00:30] wakes up
[1518-07-21 00:57] wakes up
[1518-11-23 00:57] falls asleep
[1518-09-01 00:45] wakes up
[1518-07-17 00:45] falls asleep
[1518-04-01 00:51] falls asleep
[1518-04-24 00:00] Guard #3347 begins shift
[1518-09-18 00:02] Guard #631 begins shift
[1518-05-13 00:23] wakes up
[1518-04-25 00:54] wakes up
[1518-04-14 00:02] Guard #1889 begins shift
[1518-09-12 00:13] falls asleep
[1518-08-26 00:57] falls asleep
[1518-08-13 00:02] Guard #439 begins shift
[1518-10-06 00:56] wakes up
[1518-10-01 00:32] wakes up
[1518-10-17 00:20] wakes up
[1518-07-28 00:01] falls asleep
[1518-10-28 00:59] wakes up
[1518-04-01 00:36] falls asleep
[1518-04-01 00:46] wakes up
[1518-05-24 00:01] Guard #2539 begins shift
[1518-04-24 00:41] wakes up
[1518-10-06 23:59] Guard #2903 begins shift
[1518-10-17 00:52] falls asleep
[1518-10-12 00:17] falls asleep
[1518-10-27 00:49] wakes up
[1518-08-22 00:01] falls asleep
[1518-08-16 00:57] wakes up
[1518-09-06 23:59] Guard #631 begins shift
[1518-04-17 00:01] Guard #103 begins shift
[1518-08-06 00:25] falls asleep
[1518-10-24 00:15] falls asleep
[1518-11-04 00:39] falls asleep
[1518-03-27 00:11] falls asleep
[1518-04-19 23:59] Guard #439 begins shift
[1518-10-29 00:55] wakes up
[1518-05-05 00:20] wakes up
[1518-10-02 00:49] wakes up
[1518-05-30 23:57] Guard #631 begins shift
[1518-09-11 00:41] wakes up
[1518-06-02 00:08] falls asleep
[1518-11-16 00:44] wakes up
[1518-10-27 23:52] Guard #1319 begins shift
[1518-10-10 00:00] Guard #1487 begins shift
[1518-08-16 00:54] falls asleep
[1518-05-20 00:00] falls asleep
[1518-07-29 00:14] wakes up
[1518-08-10 00:19] wakes up
[1518-08-04 00:00] Guard #439 begins shift
[1518-09-22 00:59] wakes up
[1518-11-12 23:57] Guard #1889 begins shift
[1518-11-01 00:38] falls asleep
[1518-08-09 00:56] falls asleep
[1518-07-20 00:47] wakes up
[1518-05-08 00:29] wakes up
[1518-06-12 00:56] falls asleep
[1518-06-15 23:56] Guard #2213 begins shift
[1518-05-13 00:03] Guard #1889 begins shift
[1518-10-18 00:19] falls asleep
[1518-07-10 00:30] wakes up
[1518-09-21 00:50] falls asleep
[1518-07-04 00:58] wakes up
[1518-11-09 00:46] falls asleep
[1518-05-13 00:59] wakes up
[1518-06-05 23:56] Guard #2251 begins shift
[1518-05-25 00:03] Guard #1213 begins shift
[1518-06-19 00:01] Guard #2903 begins shift
[1518-08-16 00:04] Guard #2213 begins shift
[1518-11-06 00:07] falls asleep
[1518-04-30 00:27] falls asleep
[1518-06-24 00:50] wakes up
[1518-05-27 23:58] Guard #3347 begins shift
[1518-10-22 00:22] falls asleep
[1518-05-26 00:29] wakes up
[1518-05-20 00:37] falls asleep
[1518-09-16 23:58] Guard #2903 begins shift
[1518-04-29 00:08] falls asleep
[1518-08-18 00:56] wakes up
[1518-08-22 00:42] wakes up
[1518-04-08 00:40] wakes up
[1518-08-02 00:53] wakes up
[1518-09-14 00:58] wakes up
[1518-10-06 00:07] falls asleep
[1518-04-28 00:49] wakes up
[1518-09-25 23:58] Guard #3319 begins shift
[1518-04-29 00:34] falls asleep
[1518-04-12 00:36] wakes up
[1518-05-19 00:29] wakes up
[1518-06-02 00:59] wakes up
[1518-08-03 00:24] falls asleep
[1518-05-28 00:13] falls asleep
[1518-04-15 00:02] Guard #1301 begins shift
[1518-09-30 00:32] falls asleep
[1518-07-27 23:50] Guard #1777 begins shift
[1518-06-15 00:15] falls asleep
[1518-10-09 00:03] falls asleep
[1518-04-30 00:03] Guard #1889 begins shift
[1518-09-15 00:03] falls asleep
[1518-08-30 00:20] falls asleep
[1518-06-16 00:30] falls asleep
[1518-05-08 00:03] Guard #2213 begins shift
[1518-05-17 00:51] falls asleep
[1518-06-12 00:58] wakes up
[1518-07-21 00:41] falls asleep
[1518-06-08 00:43] falls asleep
[1518-05-17 00:00] falls asleep
[1518-06-07 00:38] wakes up
[1518-05-14 00:26] falls asleep
[1518-04-16 00:26] wakes up
[1518-06-21 00:35] wakes up
[1518-10-27 00:58] wakes up
[1518-11-18 00:49] falls asleep
[1518-10-29 00:23] falls asleep
[1518-05-20 00:53] wakes up
[1518-05-18 00:00] Guard #1777 begins shift
[1518-05-24 00:44] falls asleep
[1518-11-13 00:31] falls asleep
[1518-07-16 00:58] wakes up
[1518-08-29 00:36] wakes up
[1518-10-26 23:51] Guard #2389 begins shift
[1518-07-26 00:53] falls asleep
[1518-07-14 00:56] wakes up
[1518-05-13 00:15] falls asleep
[1518-10-09 00:44] falls asleep
[1518-09-11 00:48] falls asleep
[1518-05-11 00:00] Guard #2903 begins shift
[1518-08-01 00:00] Guard #2129 begins shift
[1518-06-04 00:57] wakes up
[1518-06-19 00:42] wakes up
[1518-05-04 00:10] falls asleep
[1518-05-09 00:24] wakes up
[1518-10-21 00:51] wakes up
[1518-09-01 00:03] Guard #1319 begins shift
[1518-04-18 00:24] falls asleep
[1518-09-16 00:03] Guard #439 begins shift
[1518-07-18 00:50] falls asleep
[1518-07-20 00:06] falls asleep
[1518-05-08 00:46] wakes up
[1518-06-19 00:26] falls asleep
[1518-04-27 00:18] wakes up
[1518-07-30 00:54] falls asleep
[1518-07-31 00:45] wakes up
[1518-07-27 00:42] wakes up
[1518-08-15 00:38] wakes up
[1518-10-30 00:38] wakes up
[1518-08-04 23:57] Guard #1213 begins shift
[1518-08-01 00:41] falls asleep
[1518-08-23 00:03] Guard #829 begins shift
[1518-08-06 00:29] wakes up
[1518-11-08 00:50] wakes up
[1518-08-28 00:47] falls asleep
[1518-06-28 00:50] falls asleep
[1518-05-10 00:53] wakes up
[1518-06-11 00:54] wakes up
[1518-06-06 00:55] wakes up
[1518-10-28 23:56] Guard #103 begins shift
[1518-06-10 00:06] falls asleep
[1518-11-09 00:51] wakes up
[1518-05-16 00:18] falls asleep
[1518-03-28 00:16] falls asleep
[1518-04-19 00:53] wakes up
[1518-08-26 00:59] wakes up
[1518-09-20 00:00] Guard #2777 begins shift
[1518-10-11 00:45] falls asleep
[1518-08-09 00:00] Guard #2389 begins shift
[1518-06-01 00:46] wakes up
[1518-10-14 00:28] falls asleep
[1518-06-11 23:53] Guard #2251 begins shift
[1518-11-20 00:51] falls asleep
[1518-11-07 00:38] falls asleep
[1518-08-20 00:02] falls asleep
[1518-08-01 00:54] falls asleep
[1518-06-11 00:01] falls asleep
[1518-10-01 00:37] falls asleep
[1518-10-31 00:50] falls asleep
[1518-04-09 00:02] Guard #2129 begins shift
[1518-06-01 00:15] wakes up
[1518-04-21 00:02] Guard #2539 begins shift
[1518-07-22 00:00] falls asleep
[1518-09-28 00:58] wakes up
[1518-08-06 00:00] Guard #1319 begins shift
[1518-10-14 23:50] Guard #3319 begins shift
[1518-03-29 00:55] wakes up
[1518-08-10 00:49] wakes up
[1518-06-20 00:55] wakes up
[1518-04-19 00:08] falls asleep
[1518-11-01 00:08] falls asleep
[1518-04-01 00:01] Guard #3371 begins shift
[1518-06-29 00:33] falls asleep
[1518-08-21 00:01] Guard #1213 begins shift
[1518-08-21 23:51] Guard #1319 begins shift
[1518-06-17 00:04] falls asleep
[1518-05-22 00:54] falls asleep
[1518-11-12 00:30] wakes up
[1518-07-17 00:04] Guard #1283 begins shift
[1518-06-05 00:08] falls asleep
[1518-04-04 00:55] wakes up
[1518-07-23 00:53] wakes up
[1518-06-09 00:14] falls asleep
[1518-05-10 00:37] falls asleep
[1518-08-08 00:02] Guard #1213 begins shift
[1518-09-06 00:46] wakes up
[1518-09-07 23:57] Guard #1889 begins shift
[1518-07-18 00:55] wakes up
[1518-07-24 00:59] wakes up
[1518-05-05 00:54] wakes up
[1518-11-22 00:36] falls asleep
[1518-07-18 00:27] wakes up
[1518-10-09 00:58] wakes up
[1518-06-18 00:01] Guard #2137 begins shift
[1518-10-19 00:53] falls asleep
[1518-03-31 00:24] wakes up
[1518-09-27 00:02] falls asleep
[1518-09-12 00:56] wakes up
[1518-11-08 00:57] wakes up
[1518-10-02 00:03] Guard #1213 begins shift
[1518-06-09 00:59] wakes up
[1518-10-20 23:56] Guard #2539 begins shift
[1518-10-12 00:01] Guard #2251 begins shift
[1518-07-06 00:32] falls asleep
[1518-06-05 00:55] wakes up
[1518-10-22 00:12] wakes up
[1518-08-20 00:47] wakes up
[1518-11-17 00:42] wakes up
[1518-07-11 23:52] Guard #1283 begins shift
[1518-10-20 00:29] falls asleep
[1518-04-10 23:58] Guard #103 begins shift
[1518-07-24 00:51] falls asleep
[1518-08-12 00:50] wakes up
[1518-09-27 00:35] wakes up
[1518-10-13 00:02] Guard #103 begins shift
[1518-06-29 00:47] wakes up
[1518-10-28 00:02] falls asleep
[1518-05-10 00:44] wakes up
[1518-10-22 00:01] falls asleep
[1518-05-22 00:48] wakes up
[1518-06-19 00:56] falls asleep
[1518-06-25 00:41] falls asleep
[1518-07-07 00:51] falls asleep
[1518-10-18 00:52] wakes up
[1518-09-18 00:51] falls asleep
[1518-08-25 00:36] wakes up
[1518-06-10 00:49] wakes up
[1518-09-07 00:17] wakes up
[1518-10-01 00:31] falls asleep
[1518-04-18 00:05] falls asleep
[1518-09-23 00:01] Guard #439 begins shift
[1518-06-13 00:46] wakes up
[1518-04-20 00:30] wakes up
[1518-08-23 00:10] falls asleep
[1518-05-29 00:03] Guard #2903 begins shift
[1518-05-24 00:36] wakes up
[1518-09-24 23:57] Guard #1283 begins shift
[1518-05-28 00:46] wakes up
[1518-05-22 00:23] falls asleep
[1518-11-15 00:48] wakes up
[1518-06-01 00:21] falls asleep
[1518-05-17 00:58] wakes up
[1518-07-14 00:55] falls asleep
[1518-09-15 00:51] falls asleep
[1518-11-18 00:59] wakes up
[1518-05-25 00:17] falls asleep
[1518-08-17 00:03] Guard #3371 begins shift
[1518-04-04 00:03] Guard #1777 begins shift
[1518-09-29 00:40] wakes up
[1518-07-22 00:52] wakes up
[1518-05-07 00:04] Guard #2129 begins shift
[1518-09-27 23:54] Guard #1319 begins shift
[1518-04-29 00:09] wakes up
[1518-10-17 00:59] wakes up
[1518-07-06 00:46] wakes up
[1518-07-27 00:04] Guard #241 begins shift
[1518-08-10 00:00] Guard #241 begins shift
[1518-07-29 00:56] wakes up
[1518-07-12 00:17] wakes up
[1518-05-13 00:33] falls asleep
[1518-05-30 00:26] falls asleep
[1518-04-03 00:02] falls asleep
[1518-08-21 00:56] wakes up
[1518-04-21 00:39] wakes up
[1518-11-07 00:41] wakes up
[1518-10-24 00:03] Guard #2129 begins shift
[1518-07-16 00:53] falls asleep
[1518-04-07 00:57] wakes up
[1518-06-22 00:10] falls asleep
[1518-10-25 00:45] wakes up
[1518-09-04 00:04] falls asleep
[1518-04-14 00:58] wakes up
[1518-06-15 00:52] wakes up
[1518-04-13 00:02] Guard #3319 begins shift
[1518-07-15 00:01] Guard #1283 begins shift
[1518-07-22 00:36] wakes up
[1518-06-03 00:00] Guard #439 begins shift
[1518-05-23 00:27] falls asleep
[1518-07-16 00:00] Guard #1777 begins shift
[1518-08-15 00:06] falls asleep
[1518-05-15 00:58] wakes up
[1518-09-11 00:49] wakes up
[1518-11-19 00:55] wakes up
[1518-04-06 00:37] wakes up
[1518-07-15 00:13] falls asleep
[1518-08-28 00:03] Guard #1777 begins shift
[1518-07-21 23:51] Guard #3371 begins shift
[1518-09-08 00:27] falls asleep
[1518-07-08 00:02] Guard #241 begins shift
[1518-08-11 00:52] wakes up
[1518-09-09 00:32] falls asleep
[1518-08-13 23:59] Guard #3371 begins shift
[1518-07-19 00:53] wakes up
[1518-08-05 00:46] wakes up
[1518-07-01 00:53] falls asleep
[1518-09-11 00:03] Guard #241 begins shift
[1518-11-13 00:41] wakes up
[1518-11-11 00:39] wakes up
[1518-08-02 00:59] wakes up
[1518-09-09 00:42] wakes up
[1518-10-05 00:48] falls asleep
[1518-09-29 00:00] Guard #2903 begins shift
[1518-08-01 00:59] wakes up
[1518-04-12 00:54] wakes up
[1518-09-18 00:23] falls asleep
[1518-09-04 00:47] wakes up
[1518-04-02 00:03] Guard #1777 begins shift
[1518-04-05 23:58] Guard #2251 begins shift
[1518-05-16 00:26] wakes up
[1518-04-05 00:00] falls asleep
[1518-05-04 00:31] wakes up
[1518-06-13 00:30] falls asleep
[1518-09-06 00:50] falls asleep
[1518-07-02 00:05] falls asleep
[1518-11-08 00:04] Guard #1213 begins shift
[1518-03-29 00:08] falls asleep
[1518-04-21 00:15] falls asleep
[1518-06-30 00:39] falls asleep
[1518-10-24 23:56] Guard #1319 begins shift
[1518-05-20 00:28] wakes up
[1518-06-12 00:20] wakes up
[1518-09-11 00:29] falls asleep
[1518-05-09 00:57] falls asleep
[1518-06-03 00:33] wakes up
[1518-05-18 00:31] falls asleep
[1518-05-28 00:26] wakes up
[1518-09-26 00:35] falls asleep
[1518-05-09 00:00] Guard #3347 begins shift
[1518-10-27 00:28] falls asleep
[1518-04-25 00:38] falls asleep
[1518-11-01 00:15] wakes up
[1518-10-27 00:05] falls asleep
[1518-05-26 00:48] wakes up
[1518-09-25 00:49] wakes up
[1518-05-21 00:44] falls asleep
[1518-05-11 23:59] Guard #2137 begins shift
[1518-11-02 23:58] Guard #829 begins shift
[1518-04-29 00:37] wakes up
[1518-07-22 00:22] wakes up
[1518-03-29 00:10] wakes up
[1518-06-05 00:02] Guard #2389 begins shift
[1518-10-31 00:43] wakes up
[1518-05-03 00:43] falls asleep
[1518-05-18 00:57] wakes up
[1518-06-14 00:52] wakes up
[1518-07-10 00:27] falls asleep
[1518-05-17 00:13] wakes up
[1518-06-24 00:36] falls asleep
[1518-08-02 00:21] falls asleep
[1518-08-31 00:49] wakes up
[1518-09-09 00:09] falls asleep
[1518-08-23 00:26] wakes up
[1518-06-03 00:56] wakes up
[1518-07-24 23:57] Guard #3371 begins shift
[1518-07-16 00:45] wakes up
[1518-08-17 00:27] falls asleep
[1518-10-22 00:46] wakes up
[1518-04-12 00:22] wakes up
[1518-05-04 00:56] wakes up
[1518-10-17 00:53] wakes up
[1518-04-10 00:45] wakes up
[1518-06-29 00:34] wakes up
[1518-09-05 00:46] wakes up
[1518-04-13 00:18] falls asleep
[1518-07-27 00:24] wakes up
[1518-08-29 00:44] falls asleep
[1518-10-19 00:54] wakes up
[1518-11-02 00:41] wakes up
[1518-07-11 00:33] falls asleep
[1518-09-15 00:57] falls asleep
[1518-07-28 00:49] wakes up
[1518-06-30 23:57] Guard #1283 begins shift
[1518-09-25 00:06] falls asleep
[1518-11-04 00:28] falls asleep
[1518-10-20 00:34] wakes up
[1518-10-24 00:38] wakes up
[1518-04-21 00:57] falls asleep
[1518-06-25 00:29] wakes up
[1518-05-16 23:51] Guard #3371 begins shift
[1518-06-24 00:21] falls asleep
[1518-07-17 00:47] wakes up
[1518-09-09 00:02] Guard #1283 begins shift
[1518-07-11 00:52] wakes up
[1518-07-16 00:28] falls asleep
[1518-09-30 00:52] wakes up
[1518-06-30 00:36] wakes up
[1518-04-20 00:06] falls asleep
[1518-08-14 00:33] falls asleep
[1518-10-18 00:00] Guard #241 begins shift
[1518-09-14 00:48] wakes up
[1518-10-02 00:06] falls asleep
[1518-05-31 00:57] wakes up
[1518-05-31 00:23] falls asleep
[1518-03-28 00:33] wakes up
[1518-11-20 23:56] Guard #1283 begins shift
[1518-03-31 00:50] wakes up
[1518-07-05 00:04] Guard #2213 begins shift
[1518-08-25 00:22] falls asleep
[1518-06-20 00:03] Guard #103 begins shift
[1518-03-29 00:20] falls asleep
[1518-06-27 00:12] falls asleep
[1518-04-09 23:56] Guard #631 begins shift
[1518-06-08 00:45] wakes up
[1518-09-15 00:54] wakes up
[1518-05-22 00:02] Guard #3347 begins shift
[1518-08-09 00:59] wakes up
[1518-04-26 23:58] Guard #829 begins shift
[1518-04-25 23:56] Guard #2251 begins shift
[1518-10-27 00:57] falls asleep
[1518-06-27 00:35] wakes up
[1518-06-03 00:51] falls asleep
[1518-10-06 00:14] wakes up
[1518-10-13 00:23] wakes up
[1518-09-22 00:39] wakes up
[1518-06-02 00:34] wakes up
[1518-09-10 00:34] wakes up
[1518-07-13 00:40] falls asleep
[1518-06-14 00:03] Guard #1283 begins shift
[1518-10-04 00:32] wakes up
[1518-07-23 00:09] falls asleep
[1518-06-27 00:03] Guard #2251 begins shift
[1518-07-27 00:31] falls asleep
[1518-07-03 00:49] wakes up
[1518-10-28 00:37] wakes up
[1518-08-11 00:00] falls asleep
[1518-06-26 00:19] falls asleep
[1518-03-27 00:03] Guard #2251 begins shift
[1518-05-03 00:57] wakes up
[1518-06-28 23:56] Guard #3347 begins shift
[1518-05-05 00:04] Guard #3347 begins shift
[1518-10-14 00:14] wakes up
[1518-09-11 00:57] wakes up
[1518-05-04 00:00] Guard #3319 begins shift
[1518-06-11 00:41] wakes up
[1518-04-29 00:50] wakes up
[1518-09-17 00:41] wakes up
[1518-05-10 00:57] falls asleep
[1518-09-03 00:32] wakes up
[1518-11-01 00:02] Guard #3371 begins shift
[1518-08-01 00:43] wakes up
[1518-04-16 00:23] falls asleep
[1518-05-05 00:27] falls asleep
[1518-07-12 00:52] wakes up
[1518-04-21 00:58] wakes up
[1518-06-05 00:47] falls asleep
[1518-07-30 00:59] wakes up
[1518-08-17 23:57] Guard #631 begins shift
[1518-04-10 00:33] falls asleep
[1518-08-29 00:51] wakes up
[1518-06-02 00:56] falls asleep
[1518-06-28 00:43] wakes up
[1518-07-26 00:55] wakes up
[1518-07-12 23:59] Guard #1283 begins shift
[1518-07-05 00:53] wakes up
[1518-07-04 00:10] falls asleep
[1518-04-06 23:56] Guard #2137 begins shift
[1518-11-22 00:25] wakes up
[1518-08-15 00:52] wakes up
[1518-08-04 00:38] falls asleep
[1518-09-12 00:00] Guard #103 begins shift
[1518-11-10 00:50] wakes up
[1518-07-02 23:52] Guard #631 begins shift
[1518-05-25 23:46] Guard #631 begins shift
[1518-11-03 00:50] wakes up
[1518-06-10 23:50] Guard #439 begins shift
[1518-05-02 23:58] Guard #2539 begins shift
[1518-09-01 00:29] falls asleep
[1518-04-12 00:32] falls asleep
[1518-07-07 00:02] Guard #3371 begins shift
[1518-07-30 00:04] Guard #2129 begins shift
[1518-08-29 00:57] falls asleep
[1518-09-14 00:00] Guard #2213 begins shift
[1518-09-24 00:43] falls asleep
[1518-11-16 00:57] wakes up
[1518-05-04 00:53] falls asleep
[1518-09-29 00:34] falls asleep
[1518-09-03 00:24] falls asleep
[1518-11-16 00:06] falls asleep
[1518-08-04 00:59] wakes up
[1518-04-28 00:07] falls asleep
[1518-10-03 00:54] wakes up
[1518-10-08 00:27] falls asleep
[1518-03-27 00:57] wakes up
[1518-05-01 00:53] wakes up
[1518-06-20 23:59] Guard #1319 begins shift
[1518-04-08 00:02] Guard #1889 begins shift
[1518-06-23 23:59] Guard #241 begins shift
[1518-11-22 00:14] falls asleep
[1518-09-02 00:11] falls asleep
[1518-11-21 00:42] wakes up
[1518-08-19 23:47] Guard #1889 begins shift
[1518-04-07 00:45] falls asleep
[1518-08-25 00:15] wakes up
[1518-08-25 23:57] Guard #829 begins shift
[1518-08-10 23:47] Guard #1213 begins shift
[1518-09-16 00:09] falls asleep
[1518-07-18 00:18] falls asleep
[1518-10-26 00:01] Guard #829 begins shift
[1518-07-14 00:35] falls asleep
[1518-11-03 00:40] falls asleep
[1518-09-18 00:56] wakes up
[1518-10-31 00:10] falls asleep
[1518-08-28 00:56] wakes up
[1518-05-01 00:07] falls asleep
[1518-10-06 00:02] Guard #3347 begins shift
[1518-04-10 00:11] falls asleep
[1518-05-08 00:58] wakes up
[1518-07-24 00:24] wakes up
[1518-08-29 00:59] wakes up
[1518-06-29 00:39] falls asleep
[1518-06-13 00:52] falls asleep
[1518-06-28 00:17] falls asleep
[1518-07-29 00:07] falls asleep
[1518-06-16 00:43] wakes up
[1518-04-22 00:01] Guard #2777 begins shift
[1518-04-08 00:16] falls asleep
[1518-06-13 00:59] wakes up
[1518-06-01 00:35] wakes up
[1518-05-29 00:35] falls asleep
[1518-06-19 00:59] wakes up
[1518-09-13 00:33] falls asleep
[1518-09-28 00:31] wakes up
[1518-06-07 00:00] Guard #3319 begins shift
[1518-06-13 00:00] Guard #3347 begins shift
[1518-05-09 00:48] wakes up
[1518-05-10 00:32] wakes up
[1518-10-23 00:03] Guard #103 begins shift
[1518-10-13 00:17] falls asleep
[1518-07-17 23:59] Guard #1319 begins shift
[1518-06-04 00:38] falls asleep
[1518-09-24 00:53] wakes up
[1518-08-29 00:03] Guard #1889 begins shift
[1518-08-12 00:27] falls asleep
[1518-04-26 00:56] wakes up
[1518-07-02 00:32] wakes up
[1518-10-20 00:56] falls asleep
[1518-09-13 00:50] wakes up
[1518-08-31 00:33] falls asleep
[1518-07-08 00:39] falls asleep
[1518-10-26 00:24] wakes up
[1518-05-27 00:24] falls asleep
[1518-05-15 00:54] falls asleep
[1518-05-10 00:16] falls asleep
[1518-04-19 00:03] Guard #1319 begins shift
[1518-06-09 00:55] falls asleep
[1518-06-26 00:00] Guard #1319 begins shift
[1518-03-28 00:54] wakes up
[1518-05-09 00:46] falls asleep
[1518-11-04 00:33] wakes up
[1518-07-12 00:26] falls asleep
[1518-04-17 00:37] wakes up
[1518-05-06 00:56] wakes up
[1518-05-03 00:34] wakes up
[1518-04-02 00:10] wakes up
[1518-06-10 00:54] wakes up
[1518-03-31 00:29] falls asleep
[1518-07-22 00:39] falls asleep
[1518-09-14 00:52] falls asleep
[1518-05-05 00:17] falls asleep
[1518-06-08 00:00] Guard #2389 begins shift
[1518-07-13 00:59] wakes up
[1518-10-08 00:03] Guard #2251 begins shift
[1518-07-08 00:40] wakes up
[1518-05-14 00:39] wakes up
[1518-07-25 00:33] falls asleep
[1518-08-09 00:48] wakes up
[1518-11-12 00:01] falls asleep
[1518-08-11 00:26] wakes up
[1518-05-27 00:02] Guard #1283 begins shift
[1518-10-29 00:36] wakes up
[1518-11-14 00:43] wakes up
[1518-08-08 00:21] falls asleep
[1518-10-01 00:04] Guard #829 begins shift
[1518-11-12 00:56] falls asleep
[1518-05-12 00:52] falls asleep
[1518-04-29 00:01] Guard #1777 begins shift
[1518-08-13 00:31] falls asleep
[1518-05-07 00:48] falls asleep
[1518-04-05 00:59] wakes up
[1518-10-31 00:04] Guard #3319 begins shift
[1518-10-13 00:49] wakes up
[1518-05-14 00:19] wakes up
[1518-10-20 00:57] wakes up
[1518-09-27 00:57] wakes up
[1518-09-11 00:56] falls asleep
[1518-05-03 00:23] falls asleep
[1518-05-12 00:58] wakes up
[1518-10-08 00:54] wakes up
[1518-09-26 23:48] Guard #1213 begins shift
[1518-08-14 00:48] wakes up
[1518-05-21 00:50] wakes up
[1518-10-19 00:01] Guard #3371 begins shift
[1518-11-09 23:58] Guard #241 begins shift
[1518-10-18 00:42] wakes up
[1518-11-05 23:59] Guard #3347 begins shift
[1518-07-13 23:59] Guard #2137 begins shift
[1518-11-22 00:46] wakes up
[1518-11-17 00:31] falls asleep
[1518-06-15 00:40] falls asleep
[1518-11-09 00:09] falls asleep
[1518-09-10 00:07] falls asleep
[1518-05-15 00:19] falls asleep
[1518-09-23 00:50] wakes up
[1518-06-10 00:52] falls asleep
[1518-10-07 00:39] falls asleep
[1518-09-22 00:36] falls asleep
[1518-05-24 00:12] falls asleep
[1518-08-03 00:00] Guard #1319 begins shift
[1518-06-30 00:24] falls asleep
[1518-04-17 00:31] falls asleep
[1518-06-15 00:00] Guard #1777 begins shift
[1518-05-10 00:51] falls asleep
[1518-06-07 00:32] falls asleep
[1518-07-31 00:04] Guard #1213 begins shift
[1518-10-30 00:03] Guard #2251 begins shift
[1518-09-28 00:46] falls asleep
[1518-08-10 00:41] falls asleep
[1518-06-23 00:49] wakes up
[1518-04-13 00:47] wakes up
[1518-05-21 00:04] Guard #241 begins shift
[1518-05-19 00:19] falls asleep
[1518-04-24 00:39] falls asleep
[1518-05-15 23:56] Guard #1777 begins shift
[1518-08-09 00:15] falls asleep
[1518-04-03 00:10] wakes up
[1518-06-03 00:07] falls asleep
[1518-08-31 00:01] Guard #1213 begins shift
[1518-09-06 00:00] Guard #631 begins shift
[1518-11-05 00:01] Guard #439 begins shift
[1518-09-19 00:53] falls asleep
[1518-06-17 00:39] wakes up
[1518-08-27 00:55] wakes up
[1518-10-23 00:12] falls asleep
[1518-08-05 00:40] falls asleep
[1518-10-13 00:26] falls asleep
[1518-04-28 00:03] Guard #1889 begins shift
[1518-08-19 00:01] Guard #2903 begins shift
[1518-05-15 00:26] wakes up
[1518-06-05 00:37] wakes up
[1518-07-15 00:55] wakes up
[1518-09-17 00:56] wakes up
[1518-06-21 00:29] falls asleep
[1518-09-20 23:59] Guard #241 begins shift
[1518-04-04 00:26] wakes up
[1518-10-21 23:49] Guard #241 begins shift
[1518-09-06 00:15] falls asleep
[1518-09-19 00:33] wakes up
[1518-09-23 00:37] falls asleep
[1518-09-19 00:55] wakes up
[1518-04-02 00:08] falls asleep
[1518-07-06 00:00] Guard #2903 begins shift
[1518-07-13 00:57] falls asleep
[1518-05-23 00:00] Guard #3371 begins shift
[1518-10-02 23:58] Guard #2389 begins shift
[1518-06-24 00:30] wakes up
[1518-10-16 00:07] falls asleep
[1518-09-17 00:30] falls asleep
[1518-08-28 00:34] wakes up
[1518-07-19 00:24] falls asleep
[1518-05-31 23:57] Guard #3319 begins shift
[1518-05-01 00:00] Guard #1889 begins shift
[1518-03-30 00:56] falls asleep
[1518-10-19 23:57] Guard #1889 begins shift
[1518-08-26 23:56] Guard #2389 begins shift
[1518-07-26 00:00] Guard #2389 begins shift
[1518-07-19 00:00] Guard #2903 begins shift
[1518-11-08 00:53] falls asleep
[1518-09-14 23:48] Guard #3319 begins shift
[1518-10-03 23:58] Guard #829 begins shift
[1518-11-22 00:02] Guard #3347 begins shift
[1518-10-21 00:42] falls asleep
[1518-11-15 00:04] falls asleep
[1518-08-25 00:11] falls asleep
[1518-05-02 00:35] wakes up
[1518-10-25 00:37] falls asleep
[1518-11-11 23:48] Guard #103 begins shift
[1518-10-08 23:53] Guard #631 begins shift
[1518-10-23 00:38] falls asleep
[1518-07-16 00:34] falls asleep
[1518-11-05 00:41] falls asleep
[1518-10-31 00:41] falls asleep
[1518-11-09 00:04] Guard #1777 begins shift
[1518-06-18 00:55] wakes up
[1518-04-14 00:18] falls asleep
[1518-06-21 23:58] Guard #1777 begins shift
[1518-04-18 00:17] wakes up
[1518-04-06 00:21] falls asleep
[1518-10-05 00:57] wakes up
[1518-05-29 23:58] Guard #2389 begins shift
[1518-06-18 00:46] falls asleep
[1518-07-27 00:18] falls asleep
[1518-08-04 00:25] wakes up
[1518-06-12 00:25] falls asleep
[1518-04-16 00:55] wakes up
[1518-06-01 00:42] falls asleep
[1518-04-16 00:33] falls asleep
[1518-04-24 23:57] Guard #2389 begins shift
[1518-09-19 00:09] falls asleep
[1518-10-14 00:58] wakes up
[1518-09-16 00:53] wakes up
[1518-06-20 00:26] falls asleep
[1518-09-10 00:57] wakes up
[1518-06-16 23:50] Guard #829 begins shift
[1518-10-11 00:33] falls asleep
[1518-08-17 00:53] wakes up
[1518-08-12 00:03] Guard #1889 begins shift
[1518-05-05 23:59] Guard #2129 begins shift
[1518-10-23 00:49] wakes up
[1518-10-24 00:43] falls asleep
[1518-05-10 00:59] wakes up
[1518-04-11 00:57] falls asleep
[1518-06-15 00:35] wakes up
[1518-08-15 00:00] Guard #1777 begins shift
[1518-08-10 00:12] falls asleep
[1518-07-01 23:52] Guard #439 begins shift
[1518-06-26 00:57] wakes up
[1518-11-23 00:35] wakes up
[1518-11-05 00:51] wakes up
[1518-04-04 23:54] Guard #2389 begins shift
[1518-11-14 23:47] Guard #2903 begins shift
[1518-04-11 00:50] wakes up
[1518-10-26 00:11] falls asleep
[1518-09-06 00:56] wakes up
[1518-05-22 00:55] wakes up
[1518-06-28 00:56] wakes up
[1518-05-24 00:56] wakes up
[1518-08-11 00:46] falls asleep
[1518-06-30 00:56] wakes up
[1518-06-16 00:57] wakes up
[1518-09-27 00:46] falls asleep
[1518-06-01 00:11] falls asleep
[1518-04-12 00:07] falls asleep
[1518-08-04 00:14] falls asleep
[1518-07-22 00:34] falls asleep
[1518-08-07 00:47] falls asleep
[1518-08-29 23:59] Guard #829 begins shift
[1518-11-19 00:19] falls asleep
[1518-03-30 00:01] Guard #3347 begins shift
[1518-09-15 00:19] wakes up
[1518-08-18 00:55] falls asleep
[1518-10-01 00:45] wakes up
[1518-10-29 00:48] falls asleep
[1518-10-21 00:27] wakes up
[1518-07-03 23:56] Guard #1777 begins shift
[1518-08-06 23:59] Guard #241 begins shift
[1518-10-08 00:22] wakes up
[1518-09-22 00:52] falls asleep
[1518-06-23 00:03] Guard #241 begins shift
[1518-11-20 00:57] wakes up
[1518-03-27 23:58] Guard #3319 begins shift
[1518-09-25 00:56] falls asleep
[1518-06-06 00:52] falls asleep
[1518-09-03 23:49] Guard #2137 begins shift
[1518-08-30 00:39] wakes up
[1518-11-12 00:59] wakes up
[1518-10-23 00:25] wakes up
[1518-07-24 00:01] Guard #439 begins shift
[1518-04-12 00:52] falls asleep
[1518-11-23 00:59] wakes up
[1518-11-09 00:37] wakes up
[1518-11-22 23:57] Guard #1889 begins shift
[1518-05-11 00:27] falls asleep
[1518-09-02 00:03] Guard #3371 begins shift
[1518-10-16 00:03] Guard #3319 begins shift
[1518-05-18 23:56] Guard #3319 begins shift
[1518-09-13 00:02] Guard #1319 begins shift
[1518-11-10 00:40] falls asleep
[1518-05-13 23:56] Guard #1213 begins shift
[1518-09-25 00:59] wakes up
[1518-07-01 00:47] wakes up
[1518-10-17 00:13] falls asleep
[1518-10-12 00:31] wakes up
[1518-09-24 00:03] Guard #2137 begins shift
[1518-10-11 00:35] wakes up
[1518-07-10 00:59] wakes up"""