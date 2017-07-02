
public class getJournal {

    private String journal_code;
    private String chart_no;
    private String chart_name;
    private int journal_debit;
    private int journal_credit;
    private String journal_desc;
    private String journal_date;

    public getJournal(String journal_code, String chart_no, String chart_name, int journal_debit, int journal_credit, String journal_desc, String journal_date) {
        this.journal_code = journal_code;
        this.chart_no = chart_no;
        this.chart_name = chart_name;
        this.journal_debit = journal_debit;
        this.journal_credit = journal_credit;
        this.journal_desc = journal_desc;
        this.journal_date = journal_date;
    }

    public getJournal(String journal_date, String journal_code, String journal_desc, int journal_debit, int journal_credit) {
        this.journal_code = journal_code;
        this.journal_debit = journal_debit;
        this.journal_credit = journal_credit;
        this.journal_desc = journal_desc;
        this.journal_date = journal_date;
    }

    public String getJournal_code() {
        return journal_code;
    }

    public String getChart_no() {
        return chart_no;
    }

    public String getChart_name() {
        return chart_name;
    }

    public int getJournal_debit() {
        return journal_debit;
    }

    public int getJournal_credit() {
        return journal_credit;
    }

    public String getJournal_desc() {
        return journal_desc;
    }

    public String getJournal_date() {
        return journal_date;
    }

}
