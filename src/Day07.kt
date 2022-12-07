fun main() {

    fun part1(input: List<String>): Int {
        
        val filesystem = getFilesystem(input)

//        var dirsByPath = mutableMapOf<String, MutableList<Directory>>()
//        filesystem.directoryList.forEach{
//            var msg = it.getSize().toString()
//            while (msg.length<10) msg += " "
//            println(msg + it.fullPath.substring(0)+it.name) 
//
//            if (!dirsByPath.contains(it.fullPath)) {
//                dirsByPath.put(it.fullPath, mutableListOf<Directory>())
//            }
//            dirsByPath.get(it.fullPath)?.add(it);
//        }
//
//        return dirsByPath.map { dirList-> dirList.value.sumOf { dir -> dir.getSize() }}
//            .filter { it <= 100000 } // 1412615
//            .sumOf { it }
//        
//        var filesByPath = mutableMapOf<String, MutableList<File>>()
//        filesystem.fileList.forEach{
//            var msg = it.getSize().toString()
//            while (msg.length<10) msg += " "
//            println(msg + it.fullPath.substring(2)+it.name) 
//            
//            if (!filesByPath.contains(it.fullPath)) {
//                filesByPath.put(it.fullPath, mutableListOf<File>())
//            }
//            filesByPath.get(it.fullPath)?.add(it);
//        }
//        
//        return filesByPath.map { fileList-> fileList.value.sumOf { file -> file.getSize() }}
//            .filter { it <= 100000 } // 1412615
//            .sumOf { it }
//
//        
        return filesystem.directoryList.filter { dir -> dir.getSize() <= 100000 }.sumOf { dir -> dir.getSize() }
        
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun getFilesystem(input: List<String>): MyFileSystem {
    
    val fs = MyFileSystem("/")
    
    var currentDir = fs.root
    input.forEach { line ->
        if (line.startsWith("$")) {// it's a command
            if (line.contains(" cd ")) {
                val nextDirName = line.split(" cd ")[1]
                if (nextDirName.equals("..")) {
                    // go up one directory
                    currentDir = currentDir.parent?:currentDir
                }else if (nextDirName.equals(currentDir.name)) {
                    // do nothing, already there
                } else {
                    // go down one directory
                    currentDir = currentDir.subdirectories
                        .filter { dir -> dir.name.equals(nextDirName) }
                        .firstOrNull()?: currentDir
                }
            }
        } else { // it's file or directory
            val (objSize, objName) = line.split(" ")
            if (objSize.equals("dir")) {
                currentDir.subdirectories
                    .filter { dir -> dir.name.equals(objName) }
                    .ifEmpty { currentDir.addSubdirectory(objName) }
            } else {
                currentDir.files
                    .filter { file -> file.name.equals(objName) }
                    .ifEmpty { currentDir.addFile(objName, objSize.toInt()) }
            }
        }
        
    }

    return fs
}

class MyFileSystem(val fsName: String) {
    val root = Directory(this, null, fsName, "/")
    val directoryList = mutableListOf<Directory>()
    val fileList = mutableListOf<File>()
}

class Directory(val fs: MyFileSystem, val parent: Directory?, val name: String, path: String) {
    val fullPath = if (name .equals("/") ) "/" else path + name + "/"
    var subdirectories = mutableListOf<Directory>()
    var files = mutableListOf<File>()
    
    fun addSubdirectory(subName: String) {
        val newDir = Directory(fs, this, subName, this.fullPath)
        subdirectories.add(newDir)
        fs.directoryList.add(newDir)
    }
    
    fun addFile(fileName: String, fileSize: Int) {
        val newFile = File(fileName, fileSize, this.fullPath)
        files.add(newFile)
        fs.fileList.add(newFile)
    }
    
    fun isDirectory(): Boolean { return true}
    fun getSize(): Int {
        var sum = 0
        for (i in 0 until files.size) sum += files[i].getSize()
        for (i in 0 until subdirectories.size) sum += subdirectories[i].getSize()
        return sum
    }
    
    fun containsDirectory(dirName: String): Boolean {
        return subdirectories.filter { it.name.equals(dirName)}.size > 0
    }
}

open class File(open val name: String, val fileSize: Int, val path: String ) {
    val fullPath = path + "/"
    
    open fun getSize(): Int {
        return fileSize
    }
}
