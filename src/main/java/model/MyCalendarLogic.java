package model;

import java.util.Calendar;

public class MyCalendarLogic {
	public MyCalendar createMyCalendar(int... args) {
		//	マイカレンダーインスタンスを生成
		MyCalendar mc = new MyCalendar();
		//	現在の日時でカレンダーインスタンスを生成
		Calendar cal = Calendar.getInstance();

		//	引数が2つなら、年と月を設定
		if (args.length == 2) {
			cal.set(Calendar.YEAR, args[0]);
			//	存在しない日だと月の設定がされない場合があるので、日を1日に設定
			//	2/29とかは存在しないので、3/29とかから前月に戻れなくなる
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.MONTH, args[1]-1);
		}

		//	マイカレンダーに年を設定
		mc.setYear(cal.get(Calendar.YEAR));
		//	マイカレンダーに月を設定
		mc.setMonth(cal.get(Calendar.MONTH) + 1);
		//	月の初日に設定して、何曜日か取得し、最初の週の空白の数を取得
		cal.set(Calendar.DATE, 1);
		int before = cal.get(Calendar.DAY_OF_WEEK) - 1;
		//	月の最終日を取得
		int daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, daysCount);
		//	月の最終日の曜日から最終週の空白の数を取得
		int after = 7 - cal.get(Calendar.DAY_OF_WEEK);

		//	すべての要素の数
		int total = before + daysCount + after;
		//	カレンダーの行数
		int rows = total / 7;
		//	その行数で二次元配列を生成
		String[][] data = new String[rows][7];

		//	今見ているカレンダーが今月のカレンダーなのか判定のため、もう一つインスタンスを生成
		Calendar now = Calendar.getInstance();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < 7; j++) {
				//	最初の週で1日より前 or 最後の週で最終日より後なら空白
				if (i == 0 && j < before || i == rows - 1 && j >= (7 - after)) {
					data[i][j] = "";
				} else {
					//	カウンタ変数を実日付に変換
					int date = i * 7 + j + 1 - before;
					//	配列に日付を入れる
					data[i][j] = String.valueOf(date);
					//	今作業しているカレンダーが今月なら、今日の日付の先頭に*をつける
					if (now.get(Calendar.DATE) == date && now.get(Calendar.MONTH) == mc.getMonth() -1 && now.get(Calendar.YEAR) == mc.getYear()) {
						data[i][j] = "*" + data[i][j];
					}
				}
			}
		}

		//	作成した二次元配列をマイカレンダーにセット
		mc.setData(data);
		return mc;
	}
}
