package Algorithm_Design_Quiz7;

public class link_list {
    public int vertex;
    public int weight;
    public link_list next;
    link_list(int vertex){
        this.vertex = vertex;
        this.next = null;
        this.weight = -1;
    }
    public void setNext(int next, int weight){
        link_list a = new link_list(next);
        if(this.next != null){
            a.weight = this.weight;
            a.next = this.next;
        }
        this.next = a;
        this.weight = weight;
    }
}
