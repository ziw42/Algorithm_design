package Project3;

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
        a.weight = weight;
        if(this.next != null){
            a.next = this.next;
        }
        this.next = a;
    }
}
