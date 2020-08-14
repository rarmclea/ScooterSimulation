package workloadGenerator;

public class EventList {
	public Event head = new Event('D', 0.0);
	
	public void add(Event e){
		Event current = head;
		while (current.next != null && current.next.time < e.time)
			current = current.next;
		Event temp = current.next;
		current.next = e;
		e.next = temp;
	}
	
	public boolean isEmpty(){
		return head.next == null;
	}
	
	public Event getEvent(){
		Event current = head.next;
		if (current == null)
			return head;
		else {
			head.next = current.next;
			return current;
		}
	}
}