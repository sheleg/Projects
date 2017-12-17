time = ["10-11", "12-13", "14-15"]
days = ["Пн", "Вт", "Ср"]
worktime = Hash[days.zip(time)]
puts worktime.to_s
puts worktime["Пн"]
