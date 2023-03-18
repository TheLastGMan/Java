package Application.Model;

public class ColumnHeader
{
	public final String Header;
	public final double Width;
	public final boolean Resizeable;
	public final boolean Editable;
	
	public ColumnHeader(String header, double width, boolean resizeable, boolean editable)
	{
		Header = header;
		Width = width;
		Resizeable = resizeable;
		Editable = editable;
	}
}
