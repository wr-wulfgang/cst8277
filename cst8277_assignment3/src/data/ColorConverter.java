package data;

import java.awt.Color;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ColorConverter implements AttributeConverter<Color, String>
{
	private static final String SEPARATOR = "|";
	
	@Override
	public String convertToDatabaseColumn(Color color)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(color.getRed()).append(SEPARATOR)
			.append(color.getGreen())
			.append(SEPARATOR)
			.append(color.getBlue());
		return sb.toString();
	}
	
	@Override
	public Color convertToEntityAttribute(String colorString)
	{
		String[] rgb = colorString.split(SEPARATOR);
		return new Color(Integer.parseInt(rgb[0]),
				Integer.parseInt(rgb[1]),
				Integer.parseInt(rgb[2]),
				Integer.parseInt(rgb[3]));
	}
}
