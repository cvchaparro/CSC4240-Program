
public class Identity implements Activation
{

	@Override
	public double activate(double value) {
		return value;
	}

	@Override
	public double prime(double value) {
		return value;
	}

}
