package utilities;

public enum EndGame_ops {


	WIN("win"),
	LOST("lost"),
	UNKNOWN("unknown");

	private String option="unknown";

	private EndGame_ops(String wzor)
	{
		this.option=wzor;
	}


	public EndGame_ops get_option() {

		switch(option) {
		case "win":
			return WIN;
		case "lost":
			return LOST;
		case "unknown":
			return UNKNOWN;
		default:
			return UNKNOWN;
		}
	}
	public void set_option(String opt) {

		switch(opt) {
		case "win":
			option="win";
			break;
		case "lost":
			option="lost";
			break;
		default:
			option="unknown";
			break;
		}
	}


}
