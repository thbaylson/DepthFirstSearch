public class Vertex {
    /**
     * Unique integer corresponding to a vertex’s id number. Vertices are numbered from 0 to the
     * number of vertices - 1.
     */
    private int id;

    /** Color/Classification of the vertex. */
    private String color;

    /**
     * Vertex constructor. Receives an int to represent its id then set color to 'white'
     * @param i The int representing this vertex's id
     */
    public Vertex(int i){
        this.id = i;
        this.color = "white";
    }

    /**
     * Returns this vertex's id
     * @return vertex's id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns this vertex's color
     * @returnthis vertex's color
     */
    public String getColor(){
        return this.color;
    }

    /**
     * Sets the color of this vertex
     * @param color The color of this vertex
     */
    public void setColor(String color){
        this.color = color;
    }

    /**
     * Checks the equality of this vertex against a given object.
     * @param o The object of which its equality to this vertex is to be determined.
     * @return True if the object o has equivalent id and color values; otherwise false.
     */
    public boolean equals(Object o){
        boolean equality = false;
        if(o instanceof Vertex){
            Vertex temp = (Vertex) o;
            equality = temp.getId() == this.id;
            equality = temp.getColor().equals(this.color) && equality;
        }
        return equality;
    }//End equals

    /**
     * Constructs a string representation of the vertex object.
     * @return a string of the form "Id: this.id; Color: this.color"
     */
    public String toString(){
        return "Id: " + this.id + "; Color: " + this.color;
    }//End toString

}
