package util;

import java.util.Objects;

public class DiagnosisBLDto {

public String initDate;
public String diagnosis;
public int amount;
@Override
public int hashCode() {
	return Objects.hash(diagnosis);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	DiagnosisBLDto other = (DiagnosisBLDto) obj;
	return Objects.equals(diagnosis, other.diagnosis);
}
@Override
public String toString() {
	return "DiagnosisBLDto [initDate=" + initDate + ", diagnosis=" + diagnosis + ", amount=" + amount + "]";
}

}
