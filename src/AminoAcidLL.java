class AminoAcidLL{
  private char aminoAcid;
  private String[] codons;
  private int[] counts;
  private AminoAcidLL next;

  public AminoAcidLL(){
    aminoAcid = ' ';
    codons = new String[1];
    counts = new int[1];
    next = null;
  }


  /********************************************************************************************/
  /* Creates a new node, with a given amino acid/codon 
   * pair and increments the codon counter for that codon.
   * NOTE: Does not check for repeats!! */
  AminoAcidLL(String inCodon){
    aminoAcid = AminoAcidResources.getAminoAcidFromCodon(inCodon);
    codons = AminoAcidResources.getCodonListForAminoAcid(aminoAcid);
    counts = new int[this.codons.length];
    for(int i = 0; i < this.codons.length; i++){
      if(this.codons[i].equals(inCodon)){
        this.counts[i]++;
      }
    }
  }

  /********************************************************************************************/
  /* Recursive method that increments the count for a specific codon:
   * If it should be at this node, increments it and stops, 
   * if not passes the task to the next node. 
   * If there is no next node, add a new node to the list that would contain the codon. 
   */
  private void addCodon(String inCodon){
    int i = 0;
    String sub = "";
    String str = "";
    boolean repeats = false;

    if(i + 3 <= inCodon.length()){
      sub = inCodon.substring(i, i + 3);
      str = inCodon.substring(i + 3);
      System.out.println(sub);
    }

    AminoAcidLL iterator = this;
    while(iterator.next != null ) {
      if (iterator.aminoAcid == AminoAcidResources.getAminoAcidFromCodon(sub)) {
        repeats = true;
        for (int k = 0; k < iterator.codons.length; k++) {
          if (iterator.codons[k].equals(sub)) {
            System.out.println(sub);
            iterator.counts[k]++;
            this.addCodon(str);
            break;
          }
        }
      }
      iterator = iterator.next;
    }

    if(sub.length() > 0 && !repeats) {
      if (this.next == null) {
        if (this.aminoAcid == AminoAcidResources.getAminoAcidFromCodon(sub)) {
          for (int k = 0; k < this.codons.length; k++) {
            if (this.codons[k].equals(sub)) {
              System.out.println(sub);
              this.counts[k]++;
              this.addCodon(str);
            }
          }
        } else {
          AminoAcidLL n = new AminoAcidLL(sub);
          this.next = n;
          this.addCodon(str);
        }
      } else {
        AminoAcidLL n = new AminoAcidLL(sub);
        iterator.next = n;
        this.addCodon(str);
      }
    }
  }


  /********************************************************************************************/
  /* Shortcut to find the total number of instances of this amino acid */
  private int totalCount(){
    int count = 0;
    count++;
    return count;
  }

  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int totalDiff(AminoAcidLL inList){
    return Math.abs(totalCount() - inList.totalCount());
  }


  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int codonDiff(AminoAcidLL inList){
    int diff = 0;
    for(int i=0; i<codons.length; i++){
      diff += Math.abs(counts[i] - inList.counts[i]);
    }
    return diff;
  }

  /********************************************************************************************/
  /* Recursive method that finds the differences in **Amino Acid** counts. 
   * the list *must* be sorted to use this method */
  public int aminoAcidCompare(AminoAcidLL inList){
    return 0;
  }

  /********************************************************************************************/
  /* Same ad above, but counts the codon usage differences
   * Must be sorted. */
  public int codonCompare(AminoAcidLL inList){
    return 0;
  }


  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){
    return new char[]{};
  }

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){
    return new int[]{};
  }


  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){
    return false;
  }


  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence){
    String send = "";
    AminoAcidLL head = new AminoAcidLL(inSequence.substring(0,3));
    send = inSequence.substring(3);
    head.addCodon(send);
    return head;
  }

  /********************************************************************************************/
  /* sorts a list by amino acid character*/
  public static AminoAcidLL sort(AminoAcidLL inList){
    return null;
  }

  public void incrCodon(String codon) {

  }

  public void print(){
    AminoAcidLL iterator = this;
    for(int i = 0; i < this.counts.length; i++){
      System.out.print(this.counts[i] + " ");
    }
    while (iterator.next != null){
      System.out.println(iterator.aminoAcid + " ");
      iterator = iterator.next;
    }
  }
}