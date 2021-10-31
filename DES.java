package SimplifiedDES;

public class DES{
   public static void main(String[] args){
   
      String key = "1010000010";
      String k1 = GenerateKey1(key);
      String k2 = GenerateKey2(key);
      
      String encrypted = runDES("01110010", k1, k2);
      String decrypted = reverseDES(encrypted, k1, k2);
       
     System.out.println("Encrypted: " + encrypted);
     System.out.println("Dencrypted: " + decrypted);

   }
   
   //Start of key generation methods
   public static String GenerateKey1(String key)
   {
      String k1 =  P8(Shift1(P10(key)));
      System.out.println("Key number 1: " + k1);
      return k1;
   }
   
   public static String GenerateKey2(String key)
   {
      String k2 = P8(Shift2(Shift1(P10(key))));
      System.out.println("Key number 2: " + k2);
      return k2;
   }
   
   public static String P10(String key)
   {
      String keyp10 = "" + key.charAt(2);
      keyp10 = keyp10 + key.charAt(4);
      keyp10 = keyp10 + key.charAt(1);
      keyp10 = keyp10 + key.charAt(6);
      keyp10 = keyp10 + key.charAt(3);
      keyp10 = keyp10 + key.charAt(9);
      keyp10 = keyp10 + key.charAt(0);
      keyp10 = keyp10 + key.charAt(8);
      keyp10 = keyp10 + key.charAt(7);
      keyp10 = keyp10 + key.charAt(5);
      
      return keyp10;
   }
   
   public static String Shift1(String keyp10)
   {
      String leftHalf = "";
      String rightHalf = "";
      
      for(int i = 0; i < keyp10.length()/2; i++)
      {
         leftHalf = leftHalf + keyp10.charAt(i);
      }
      for(int i = keyp10.length()/2; i < keyp10.length(); i++)
      {
         rightHalf = rightHalf + keyp10.charAt(i);
      }
      
      //char array shifted for the first half
      char temp = leftHalf.charAt(0);
      char[] a = new char[leftHalf.length()];
      for(int i = a.length-1; i >= 1; i--)
      {
         a[i-1] = leftHalf.charAt(i); 
      }
      a[a.length-1] = temp;
      
      //char array shifted for the second half
      char temp1 = rightHalf.charAt(0);
      char[] a1 = new char[rightHalf.length()];
      for(int i = a1.length-1; i >= 1; i--)
      {
         a1[i-1] = rightHalf.charAt(i); 
      }
      a1[a1.length-1] = temp1;
      
      // add the char arrays together to form a string
      String shift1 = new String(a);
      
      for(int i = 0; i < a1.length; i++)
      {
         shift1 = shift1 + a1[i];
      }
      return shift1;
   }
   
   //shift2 uses the string after shift1
   public static String Shift2(String shift1)
   {
      char[] firstHalf = new char[shift1.length()/2]; //this method uses arrays from start
      char[] secondHalf = new char[shift1.length()/2];
      
     int j = 0; //fill the arrays
     for(int i = 0; i < shift1.length()/2; i++)
     {
         firstHalf[j] = shift1.charAt(i);
         j++;
     }
     j = 0;
     for(int i = shift1.length()/2; i < shift1.length(); i++)
     {
         secondHalf[j] = shift1.charAt(i);
         j++;
     }
      //end of fill arrays
           
      shiftArrayBy2(firstHalf);//helper method see below
      shiftArrayBy2(secondHalf);
      
      String shift2 = new String(firstHalf);//create the string that will be retured
      for(int i = 0; i < secondHalf.length; i++)// add the second half to the array
      {
         shift2 = shift2 + secondHalf[i];
      }
      return shift2;
   }
   //shiftArrayBy2 helper method it is void
   public static void shiftArrayBy2(char[]a)
   {
      char temp1 = a[0];
      char temp2 = a[1];
      
      int i, j;//trace it from here if you don't understand
      for(i = 0, j = 2; i < a.length && j < a.length; i++, j++)
      {
         a[i] = a[j];
      }
      a[a.length-2] = temp1;
      a[a.length-1] = temp2;
   }
   
   public static String P8(String shift)
   {
      String keyp8 = "" + shift.charAt(5);
      keyp8 = keyp8 + shift.charAt(2);
      keyp8 = keyp8 + shift.charAt(6);
      keyp8 = keyp8 + shift.charAt(3);
      keyp8 = keyp8 + shift.charAt(7);
      keyp8 = keyp8 + shift.charAt(4);
      keyp8 = keyp8 + shift.charAt(9);
      keyp8 = keyp8 + shift.charAt(8);
      
      return keyp8;
   }
   //***************** END OF KEY GENERATION **********************************
   
   //******************START ROUNDS********************************************
   
   public static String runDES (String text, String k1, String k2)
   {
      return InverseIP(Fk2(SW(Fk1(IP(text), k1)), k2)); 
   }
   
   public static String reverseDES(String text, String k1, String k2)
   {
      return InverseIP(Fk1(SW(Fk2(IP(text), k2)), k1));
   }
   
   public static String IP (String text)
   {
      String ip = "" + text.charAt(1);
      ip = ip + text.charAt(5);
      ip = ip + text.charAt(2);
      ip = ip + text.charAt(0);
      ip = ip + text.charAt(3);
      ip = ip + text.charAt(7);
      ip = ip + text.charAt(4);
      ip = ip + text.charAt(6);
      return ip;
   }
   
     public static String InverseIP (String text)
   {
      String ip = "" + text.charAt(3);
      ip = ip + text.charAt(0);
      ip = ip + text.charAt(2);
      ip = ip + text.charAt(4);
      ip = ip + text.charAt(6);
      ip = ip + text.charAt(1);
      ip = ip + text.charAt(7);
      ip = ip + text.charAt(5);
      return ip;
   }
   
   public static String SW(String text)
   {
      String switched = "";
      
      for(int i = text.length()/2; i < text.length(); i++)
      {
         switched = switched + text.charAt(i);
      }
      for(int i = 0; i < text.length()/2; i++)
      {
         switched = switched + text.charAt(i);
      }
      return switched; 
   }
   
   public static String Fk1(String text, String k1)
   {
      String left = text.substring(0, text.length()/2);
      String right = text.substring(text.length()/2);
      
      String right1 = new String(right);
      right1 = XOR(P4(SBoxes(XOR(ExpandAndPermutate(right1), k1))),left);
      
      return right1.concat(right);
   }
   
   public static String Fk2(String text, String k2)
   {
      String left = text.substring(0, text.length()/2);
      String right = text.substring(text.length()/2);
      
      String right1 = new String(right);
      right1 = XOR(P4(SBoxes(XOR(ExpandAndPermutate(right1), k2))),left);
      
      return right1.concat(right);
   }
   
   public static String XOR(String text, String key)
   {
      String xor = "";
      
      for(int i = 0; i < text.length(); i++)
      {
         if(text.charAt(i) == key.charAt(i))
         {
            xor = xor + "0";
         }
         else
         {
            xor = xor + "1";
         }
      }
      return xor;
   }
   
   public static String ExpandAndPermutate(String s)
   {
      String EP = "" + s.charAt(3);
      EP = EP + s.charAt(0);
      EP = EP + s.charAt(1);
      EP = EP + s.charAt(2);
      EP = EP + s.charAt(1);
      EP = EP + s.charAt(2);
      EP = EP + s.charAt(3);
      EP = EP + s.charAt(0);
      
      return EP;
   }
   
   public static String P4(String s)
   {
      String p4 = "" + s.charAt(1);
      p4 = p4 + s.charAt(3);
      p4 = p4 + s.charAt(2);
      p4 = p4 + s.charAt(0);

      return p4;
   }
   
   public static String SBoxes(String text)
   {
      String left = S0(text.substring(0, text.length()/2));
      String right = S1(text.substring(text.length()/2));
      
      return left.concat(right);
   }
   
   public static String S0(String left)
   {
      String[][] s0 = {{"01", "00", "11", "10"},
                       {"11", "10", "01", "00"},
                       {"00", "10", "01", "11"},
                       {"11", "01", "11", "10"}};
                       
       String RowIs = "" + left.charAt(0) +  left.charAt(3);
       String ColumnIs = "" + left.charAt(1) +  left.charAt(2);

       int row = getRow(RowIs);            
       int column = getColumn(ColumnIs);
       
       return s0[row][column];
   }
   
   public static String S1(String right)
   {
      String[][] s1 = {{"00", "01", "10", "11"},
                       {"10", "00", "01", "11"},
                       {"11", "00", "01", "00"},
                       {"10", "01", "00", "11"}};
                       
       String RowIs = "" + right.charAt(0) +  right.charAt(3);
       String ColumnIs = "" + right.charAt(1) +  right.charAt(2);

       int row = getRow(RowIs);            
       int column = getColumn(ColumnIs);
       
       return s1[row][column];
   }
   
   public static int getRow(String s)
   {
      if(s.equals("00")){return 0;}
      else if(s.equals("01")){return 1;}
      else if(s.equals("10")){return 2;}
      else if(s.equals("11")){return 3;}
      return -1;
   }
   
   public static int getColumn(String s)
   {
      if(s.equals("00")){return 0;}
      else if(s.equals("01")){return 1;}
      else if(s.equals("10")){return 2;}
      else if(s.equals("11")){return 3;}
      return -1;
   }

}

