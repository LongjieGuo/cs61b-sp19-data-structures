public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> convertedC = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            convertedC.addLast(word.charAt(i));
        }
        return convertedC;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> lst = wordToDeque(word);
        return isPalindromeHelper(lst);
    }

    private boolean isPalindromeHelper(Deque<Character> lst) {
        if (lst.size() == 0 || lst.size() == 1) {
            return true;
        } else {
            Character front = lst.removeFirst();
            Character end = lst.removeLast();
            if (front != end) {
                return false;
            } else {
                return isPalindromeHelper(lst);
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> lst = wordToDeque(word);
        return isPalindromeHelper2(lst, cc);
    }

    private boolean isPalindromeHelper2(Deque<Character> lst, CharacterComparator cc) {
        if (lst.size() == 0 || lst.size() == 1) {
            return true;
        } else {
            Character front = lst.removeFirst();
            Character end = lst.removeLast();
            if (!cc.equalChars(front, end)) {
                return false;
            } else {
                return isPalindromeHelper2(lst, cc);
            }
        }
    }
}
