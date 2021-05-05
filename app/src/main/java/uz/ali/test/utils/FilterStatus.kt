package uz.ali.test.utils

class FilterStatus {
   public fun getStatus(status: String): String {
        var a = ""
        if (status.equals("unknown")) {
            a = "#9f9f9f"
        } else if (status.equals("Dead")) {
            a = "#d73a2a"
        } else if (status.equals("Alive")) {
            a = "#54cd42"
        } else {
            a = "#000"
        }
        return a
    }
}