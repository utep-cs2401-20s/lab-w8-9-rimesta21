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
    this.incrCodon(inCodon);
    next = null;
  }

  /********************************************************************************************/
  /* Recursive method that increments the count for a specific codon:
   * If it should be at this node, increments it and stops, 
   * if not passes the task to the next node. 
   * If there is no next node, add a new node to the list that would contain the codon. 
   */
  private void addCodon(String inCodon) {
    //this checks to see if we already have the AminoAcid
    if (aminoAcid == AminoAcidResources.getAminoAcidFromCodon(inCodon)) {
      incrCodon(inCodon);
    } else if (next != null) {
      next.addCodon(inCodon); //this goes through the link list seeing if the AminoAcid was already created
    } else {
      next = new AminoAcidLL(inCodon); //if the AminoAcid has not been created then we create it
    }
  }



  /********************************************************************************************/
  /* Shortcut to find the total number of instances of this amino acid */
  private int totalCount(){
    int sum = 0;
    for(int i = 0; i < counts.length; i++){
      sum += counts[i];
    }
    return sum;
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
      if(inList.aminoAcid == ' ' && aminoAcid == ' ') { //this checks if both list are empty, if so return an error
        return -1;
      } else if (inList.aminoAcid == ' ') { //this checks if the inLIst is empty and list is not
        if(next != null){ // if list has more nodes it counts  this node and transverse through the rest
          return totalCount() + next.aminoAcidCompare(inList);
        } else { //this means you are at the end of the list so just get the total counts
          return totalCount();
        }
      } else if(aminoAcid == ' ') {//Same as above but in the case were list is empty and inLIst is not
        if(inList.next != null){
          return inList.totalCount() + aminoAcidCompare(inList.next);
        } else {
          return inList.totalCount();
        }
         /*if you get here, neither list is empty and we are going to start comparing. I would make this section it's own
        method so that it would only have to check if the lists are empty once but I'm not sure that's allowed.
       */
        //Since the list are sorted, this checks to see if inList has a character that list doesn't
      } else if (aminoAcid > inList.aminoAcid) {
        if(inList.next != null) {//if inList has more nodes we compare those node with list current node
          return inList.totalCount() + aminoAcidCompare(inList.next);
        } else {//otherwise we transverse list and get all of its counts
          return inList.totalCount() + transverseCount();
        }
      } else if (aminoAcid < inList.aminoAcid) {//Same as above but now list has a character that inList does not have
        if(next != null) {
          return totalCount() + next.aminoAcidCompare(inList);
        } else {
          return totalCount() + inList.transverseCount();
        }
      } else {//if you get here both inList and list have the same AminoAcid
        if(next != null && inList.next != null) {//if both lists have more nodes, transverse and compare the other nodes
          return totalDiff(inList) + next.aminoAcidCompare(inList.next);
        } else if(next != null) {//if list has more nodes and inLIst doesn't, get difference and transverse through list
          return totalDiff(inList) + transverseCount();
        } else if(inList.next != null) {//Same as above but now inList has more nodes and list doesn't
          return totalDiff(inList) + inList.transverseCount();
        } else {//neither list and inList have more nodes so just return their difference
          return totalDiff(inList);
        }
      }
  }

  private int transverseCount() {
    if(next == null) {
      return totalCount();
    }
    return totalCount() + next.totalCount();
  }

  /********************************************************************************************/
  /* Same ad above, but counts the codon usage differences
   * Must be sorted. */
  public int codonCompare(AminoAcidLL inList){
    if(inList.aminoAcid == ' ' && aminoAcid == ' ') { //this checks if both list are empty, if so return an error
      return -1;
    } else if (inList.aminoAcid == ' ') { //this checks if the inLIst is empty and list is not
      if(next != null){ // if list has more nodes it the counts for this node and transverse through
        return totalCount() + next.codonCompare(inList);
      } else { //this means you are at the end of the list so just get the total counts
        return totalCount();
      }
    } else if(aminoAcid == ' ') {//Same as above but in the case were list is empty and inLIst is not
      if(inList.next != null){
        return inList.totalCount() + codonCompare(inList.next);
      } else {
        return inList.totalCount();
      }
      //Since the list are sorted, this checks to see if inList has a character that list doesn't
    } else if (aminoAcid > inList.aminoAcid) {
      if(inList.next != null) {//if inList has more nodes we compare those node with list current node
        return inList.totalCount() + codonCompare(inList.next);
      } else {//otherwise we transverse list and get all of its counts
        return inList.totalCount() + transverseCount();
      }
    } else if (aminoAcid < inList.aminoAcid) {//Same as above but now list has a character that inList does not have
      if(next != null) {
        return totalCount() + next.codonCompare(inList);
      } else {
        return totalCount() + inList.transverseCount();
      }
    } else {//if you get here both inList and list have the same AminoAcid
      if(next != null && inList.next != null) {//if both lists have more nodes, transverse and compare the other nodes
        return codonDiff(inList) + next.codonCompare(inList.next);
      } else if(next != null) {//if list has more nodes and inLIst doesn't, get difference and transverse through list
        return codonDiff(inList) + transverseCount();
      } else if(inList.next != null) {//Same as above but now inList has more nodes and list doesn't
        return codonDiff(inList) + inList.transverseCount();
      } else {//neither list and inList have more nodes so just return their difference
        return codonDiff(inList);
      }
    }
  }


  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){
    //if we have gotten to the end of the list then return the aminoAcid
    if(next == null){
      return new char[]{aminoAcid};
    } else {//transverse through the list until you get into the end
      char[] aminoAcids = next.aminoAcidList();
      String temp = new String(aminoAcids);
      temp = "" + aminoAcid + temp; //add the previous Acid and current Acid in the right order
      return temp.toCharArray(); //Create String into char array
    }
  }

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){
    //Once you get to the end just return the current counts of the last node
    if(next == null){
      return new int[]{totalCount()};
    } else {
      int[] aminoCounts = next.aminoAcidCounts();
      int[] temp = new int[aminoCounts.length + 1];
      //copy the array that's been building, leaving a space in the front, and adding to the length by one
      System.arraycopy(aminoCounts, 0, temp, 1, aminoCounts.length);
      //current totalCount goes in the front of the array
      temp[0] = totalCount();
      return temp;
    }
  }


  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){
      if(next == null){
        return true;
      } else if (aminoAcid > next.aminoAcid) {
        return false;
      } else {
        return next.isSorted();
      }
  }


  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence){
    String sub = "";
    String str = "";

    //makes sure we have a long enough string and or the string can but cut into 3s
    if(inSequence.length() < 3 || inSequence.length() % 3 != 0) {
      System.out.println("Check your RNA string.");
      return null;
    }

    AminoAcidLL head = new AminoAcidLL(inSequence.substring(0,3));
    str = inSequence.substring(3);

    //this parses the string and sends a codon to addCodon
    while(str.length() != 0){
      sub = str.substring(0, 3);
      head.addCodon(sub);
      str = str.substring(3);
    }
    return head;
  }

  /********************************************************************************************/
  /* sorts a list by amino acid character*/
  //I got this insert sorting algorithm from Zybooks. I had to create the helper methods. I made sure to understand
  // the code before implementing it
  public static AminoAcidLL sort(AminoAcidLL inList){
    AminoAcidLL beforeCurr = inList;
    AminoAcidLL currNode = beforeCurr.next;
    while(currNode != null) {
      AminoAcidLL next = currNode.next; //makes sure we don't lose the node
      //finds where the node should be
      AminoAcidLL position = currNode.ListFindInsertPosition(inList, currNode.aminoAcid);

      //if the node is where it already need to be then leave it there
      if(position == beforeCurr) {
        beforeCurr = currNode;
      } else {
        beforeCurr.ListRemoveAfter(beforeCurr);
        if(position == null) {//the node should be in the front
          inList = currNode.ListPrepend(inList, currNode);
        } else {//insert the node where it needs to be
          currNode.ListInsertAfter(position,currNode);
        }
      }
      currNode = next;
    }
    return inList;
  }

  private AminoAcidLL ListFindInsertPosition(AminoAcidLL inList, char data){
    AminoAcidLL currNodeA = null;
    AminoAcidLL currNodeB = inList;

    //the moments the data is less than where we are looking it stops and returns the node that the current node should
    //be put after. If it return null then put the node in the front
    while(currNodeB != null && data > currNodeB.aminoAcid){
      currNodeA = currNodeB;
      currNodeB = currNodeB.next;
    }

    return currNodeA;
  }

  private void ListRemoveAfter(AminoAcidLL target) {//removes the currNode
    AminoAcidLL temp = target.next.next;
    target.next = temp;
  }

  private AminoAcidLL ListPrepend(AminoAcidLL inList, AminoAcidLL currNode) {//puts currNode in the front
    currNode.next = inList;
    return inList = currNode;
  }

  private void ListInsertAfter(AminoAcidLL position, AminoAcidLL currNode) {//inserts currNode where it needs to be
    AminoAcidLL temp = position.next;
    position.next = currNode;
    currNode.next = temp;
  }

  //this method increases the codon count
  public void incrCodon(String codon) {
    for(int i = 0; i < codons.length; i++){
      if(codons[i].equals(codon)){
        counts[i]++;
      }
    }
  }

  public char getAminoAcid() {
    return aminoAcid;
  }

  public AminoAcidLL getNext() {
    return next;
  }

  public int[] getCounts() {
    return counts;
  }

  public int getTotalCounts() {
    return totalCount();
  }

  public char[] getAminoAcidList() {
    return aminoAcidList();
  }

  public int[] getAminoAcidCounts() {
    return aminoAcidCounts();
  }

  public AminoAcidLL getSort() {
    return sort(this);
  }

}