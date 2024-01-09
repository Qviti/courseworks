// student ID: 38883147

"use strict";

const cwlib = require('./cwlib.js');

let curState = {
    line: 0, 												// current line number
    curLine: '', 											// current line content
    curLineSub: [], 										// balanced substrings in the current line
    allLongest: [], 										// longest balanced substrings of all lines
    lineLongest: [] 										// longest balanced substrings for each line
};

/**
 * checks if substring is balanced.
 * substring is balanced if each alphabet character appears in both
 * lowercase and uppercase the same number of times.
 * @param substring the substring to check.
 * @param index current index in the substring.
 * @param charCount to track count of each character.
 * returns true if the substring is balanced if not then false.
 */
function isBalanced(substring, index = 0, charCount = {}) {
    // end of the substring
    if (index >= substring.length) {
        return Object.values(charCount).every(count => count.lower === count.upper);
    }

    const char = substring[index];
    // check alphabet characters
    if (char.match(/[a-zA-Z]/)) {
        const lowerChar = char.toLowerCase();
        charCount[lowerChar] = charCount[lowerChar] || { lower: 0, upper: 0 };
        char === lowerChar ? charCount[lowerChar].lower++ : charCount[lowerChar].upper++;
    }

    // recursively calls for next character (no loops)
    return isBalanced(substring, index + 1, charCount);
}

/**
 * processes each character received from the input.
 * builds the current line and checks for balanced substrings.
 * @param char the current character for process.
 */
function processCharacter(char) {
    curState.curLine += char;

    // recursively checks all substrings ending with the current character
    function checkSubstrings(startIndex = 0) {
        if (startIndex > curState.curLine.length - 1) {
            return; // if no more substrings to check
        }

        const substring = curState.curLine.substring(startIndex);
        if (isBalanced(substring)) {
            curState.curLineSub.push([startIndex, curState.curLine.length - 1]);
            console.log(`[ ${startIndex}, ${curState.curLine.length - 1} ]`);
        }

        checkSubstrings(startIndex + 1); // recursively call next substring
    }

    checkSubstrings(); // start checking substrings for the current character
}

/**
 * resets line state at the end of each line
 * and updates the longest balanced substrings for the current line and overall.
 */
function resetLine() {
    let max = Math.max(...curState.curLineSub.map(substring => substring[1] - substring[0] + 1));

    // filter the longest balanced substrings for the current line
    curState.lineLongest[curState.line] = curState.curLineSub.filter(substring => substring[1] - substring[0] + 1 === max);

    // print the longest balanced substrings for the current line
    if (curState.lineLongest[curState.line].length > 0) {
        console.log(`Longest balanced substrings for this line: `, curState.lineLongest[curState.line]);
    } else {
        console.log(`Longest balanced substrings for this line:  []`);
    }
    console.log();

    // update overall longest balanced substrings
    if (!curState.allLongest.length || max > curState.allLongest[0][2] - curState.allLongest[0][1] + 1) {
        curState.allLongest = curState.lineLongest[curState.line].map(substring => [curState.line, ...substring]);
    } else if (max === curState.allLongest[0][2] - curState.allLongest[0][1] + 1) {
        curState.allLongest = curState.allLongest.concat(curState.lineLongest[curState.line].map(substring => [curState.line, ...substring]));
    }

    // reset current line state for the next line
    curState.curLine = '';
    curState.curLineSub = [];
    curState.line++;
}

/**
 * processes end of the input
 * and prints the longest balanced substrings across all lines.
 */
function endProcessing() {
    console.log('Overall longest balanced substrings:');
    curState.allLongest.forEach(substring => {
        console.log(`[ ${substring[0]}, [ ${substring.slice(1).join(', ')} ] ]`);
    });
}

// cwlib stuff
cwlib.on('ready', function() {
    cwlib.run(); 
});

cwlib.on('data', function(data) {
    processCharacter(data);
});

cwlib.on('reset', function() {
    resetLine();
});

cwlib.on('end', function() {
    endProcessing();
});

cwlib.setup(process.argv[2]);